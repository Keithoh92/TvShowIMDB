package com.example.moviedd.ui.tv.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedd.common.Resource
import com.example.moviedd.data.database.helper.AutoCompleteSearchSystem
import com.example.moviedd.domain.api.repository.TvShowApiRepo
import com.example.moviedd.domain.database.repository.TvShowDBRepo
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.tv.event.TvShowScreenEvent
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TvShowScreenViewModel @Inject constructor(
    private val tvShowDBRepository: TvShowDBRepo,
    private val tvShowApiRepo: TvShowApiRepo,
    private val autoCompleteSearchSystem: AutoCompleteSearchSystem
): ViewModel() {

    private val _uiState = MutableStateFlow(TvShowScreenUIState())
    val uiState = _uiState.asStateFlow()

    private var tvShowsList = listOf<ShowInfo>()

    fun onEvent(event: BaseComposeEvent) {
        when (event) {
            is TvShowScreenEvent.OnSortOptionChosen -> onSortOptionChosen(event.sortingOption)
            is TvShowScreenEvent.OnDescriptionClicked -> onDescriptionClicked(event.showId, event.isMinimised ?: false)
            is TvShowScreenEvent.SetScrollToStopToFalse -> _uiState.update { it.copy(shouldScrollToTop = false) }
            is TvShowScreenEvent.OnViewChanged -> updateCardLayout(event.isGridView)
            is TvShowScreenEvent.OnRefresh -> onRefresh()
            is TvShowScreenEvent.OnSearchTextChanged -> onSearchTextChanged(event.prefix)
            is TvShowScreenEvent.OnSelectSearchedTvShow -> onSelectSearchedTvShow(event.id)
            is TvShowScreenEvent.SetScrollToIdToFalse -> resetScrollToId()
        }
    }

    init {
        getTvShowsFromApi()
        updateScreenUIState()
    }

    private fun updateScreenUIState() = viewModelScope.launch {
        val tvShows = tvShowDBRepository.getTvShows()
        tvShowsList = tvShows
        if (tvShows.isEmpty()) { _uiState.update { it.copy(noTvShowsReturned = true) } }

        val updatedMap = updateTvShowsDescriptionsMinimisedMap(tvShows)

        _uiState.update { it.copy(tvShowList = tvShows, descriptionMinimised = updatedMap) }
        delay(2000)
        _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
    }

    private fun updateTvShowsDescriptionsMinimisedMap(tvShows: List<ShowInfo>): Map<Int, Boolean> {
        val updatedMap = mutableMapOf<Int, Boolean>()
        tvShows.forEach {
            updatedMap[it.id] = true
        }

        return updatedMap
    }

    private fun resetScrollToId() {
        _uiState.update { it.copy(scrollToShowByID = Pair(false, -1)) }
    }

    private fun onSelectSearchedTvShow(id: Int) {
        _uiState.update { it.copy(
            scrollToShowByID = Pair(true, id),
            searchedTvShows = emptyList()
        )}
    }

    private fun onSearchTextChanged(prefix: String) {
        val tvShows =  autoCompleteSearchSystem.search(prefix.lowercase())

        val listOfSearchedTvShowsAndIds = tvShows.map { suggestedTvShow ->
            val tvShowId = tvShowsList.find {
                it.title.lowercase() == suggestedTvShow.lowercase()
            }?.id ?: -1

            Pair(tvShowId, suggestedTvShow)
        }

        _uiState.update {
            it.copy(
                searchedTvShows = if (prefix.isBlank()) emptyList() else listOfSearchedTvShowsAndIds
            )
        }
    }

    private fun updateCardLayout(gridView: Boolean) {
        _uiState.update { it.copy(isGridView = !gridView) }
    }

    private fun onRefresh() {
        _uiState.update { it.copy(isRefreshing = true) }
        getTvShowsFromApi()
        updateScreenUIState()
    }

    private fun onDescriptionClicked(showId: Int, isMinimised: Boolean) {
        val updatedMap = uiState.value.descriptionMinimised.toMutableMap()
        updatedMap[showId] = !isMinimised

        _uiState.update { it.copy(descriptionMinimised = updatedMap) }
    }

    private fun onSortOptionChosen(sortingOption: String) {
        _uiState.update { it.copy(shouldScrollToTop = true) }
        when (sortingOption) {
            "Alphabetically" -> sortTvShowsAlphabetically()
            "Top Rated" -> sortByRating()
            else -> sortTvShowsByAirDate()
        }
    }

    private fun sortByRating() = viewModelScope.launch {
        val tvShows = tvShowDBRepository.getTvShows()
        tvShowsList = tvShows.sortedWith(compareByDescending { it.rating })
        updateMapAndSortUITvShowList()
    }

    private fun sortTvShowsByAirDate() = viewModelScope.launch {
        val tvShows = tvShowDBRepository.getTvShows()
        val comparator = Comparator { s1: ShowInfo, s2: ShowInfo -> s1.airDate.compareTo(s2.airDate) }
        tvShowsList = tvShows.sortedWith(comparator)
        updateMapAndSortUITvShowList()
    }

    private fun sortTvShowsAlphabetically() = viewModelScope.launch {
        val tvShows = tvShowDBRepository.getTvShows()
        tvShowsList = tvShows.sortedWith(compareBy { it.title })
        updateMapAndSortUITvShowList()
    }

    private fun updateMapAndSortUITvShowList() {
        val updatedMap = updateTvShowsDescriptionsMinimisedMap(tvShowsList)
        _uiState.update { it.copy(tvShowList = tvShowsList, descriptionMinimised = updatedMap) }
    }

    private fun getTvShowsFromApi() = runBlocking {
        tvShowApiRepo.downloadTvShows().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val tvShowsList = result.data?.map {
                        val imagePathUrl = "https://image.tmdb.org/t/p/w500${it.imagePath}"
                        ShowInfo(it.id, it.title, imagePathUrl, it.airDate, it.description, it.rating)
                    }
                    if (!tvShowsList.isNullOrEmpty()) {
                        saveTvShows(tvShowsList)
                    }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(
                        error = result.message ?: "An unexpected error occurred"
                    ) }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private suspend fun saveTvShows(tvShows: List<ShowInfo>) {
        tvShows.map {
            tvShowDBRepository.insert(it)
            autoCompleteSearchSystem.insert(it.title.lowercase())
        }
    }
}