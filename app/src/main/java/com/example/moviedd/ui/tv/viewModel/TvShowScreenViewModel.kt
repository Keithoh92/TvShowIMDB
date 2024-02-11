package com.example.moviedd.ui.tv.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TvShowScreenViewModel @Inject constructor(
    private val tvShowDBRepository: TvShowDBRepo,
    private val tvShowApiRepo: TvShowApiRepo,
    private val autoCompleteSearchSystem: AutoCompleteSearchSystem
): ViewModel() {

    var tvShowScreenUIState by mutableStateOf(TvShowScreenUIState())
        private set

    private var tvShowsList = listOf<ShowInfo>()

    fun onEvent(event: BaseComposeEvent) {
        when (event) {
            is TvShowScreenEvent.OnSortOptionChosen -> onSortOptionChosen(event.sortingOption)
            is TvShowScreenEvent.OnDescriptionClicked -> onDescriptionClicked(event.showId, event.isMinimised ?: false)
            is TvShowScreenEvent.SetScrollToStopToFalse -> tvShowScreenUIState = tvShowScreenUIState.copy(shouldScrollToTop = false)
            is TvShowScreenEvent.OnViewChanged -> updateCardLayout(event.isGridView)
            is TvShowScreenEvent.OnRefresh -> onRefresh()
            is TvShowScreenEvent.OnSearchTextChanged -> onSearchTextChanged(event.prefix)
            is TvShowScreenEvent.OnSelectSearchedTvShow -> onSelectSearchedTvShow(event.title)
        }
    }

    private fun onSelectSearchedTvShow(title: String) {
        TODO("Not yet implemented")
    }

    private fun onSearchTextChanged(prefix: String) {
        var tvShows =  autoCompleteSearchSystem.search(prefix.lowercase())
        if (prefix.isBlank()) tvShows = emptyList()
        tvShowScreenUIState = tvShowScreenUIState.copy(searchedTvShows = tvShows)
    }

    init {
        getTvShowsFromApi()
        updateScreenUIState()
    }

    private fun updateScreenUIState() = viewModelScope.launch {
        val tvShows = tvShowDBRepository.getTvShows()
        if (tvShows.isEmpty()) { tvShowScreenUIState = tvShowScreenUIState.copy(noTvShowsReturned = true) }

        val updatedMap = updateTvShowsDescriptionsMinimisedMap(tvShows)

        tvShowScreenUIState = tvShowScreenUIState.copy(tvShowList = tvShows, descriptionMinimised = updatedMap)
        delay(2000)
        tvShowScreenUIState = tvShowScreenUIState.copy(isLoading = false, isRefreshing = false)
    }

    private fun updateTvShowsDescriptionsMinimisedMap(tvShows: List<ShowInfo>): Map<Int, Boolean> {
        val updatedMap = mutableMapOf<Int, Boolean>()
        tvShows.forEach {
            updatedMap[it.id] = true
        }

        return updatedMap
    }

    private fun updateCardLayout(gridView: Boolean) {
        tvShowScreenUIState = tvShowScreenUIState.copy(isGridView = !gridView)
    }

    private fun onRefresh() {
        tvShowScreenUIState = tvShowScreenUIState.copy(isRefreshing = true)
        getTvShowsFromApi()
        updateScreenUIState()
    }

    private fun onDescriptionClicked(showId: Int, isMinimised: Boolean) {
        val updatedMap = tvShowScreenUIState.descriptionMinimised.toMutableMap()
        updatedMap[showId] = !isMinimised

        tvShowScreenUIState = tvShowScreenUIState.copy(descriptionMinimised = updatedMap)
    }

    private fun onSortOptionChosen(sortingOption: String) {
        tvShowScreenUIState = tvShowScreenUIState.copy(shouldScrollToTop = true)
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
        tvShowScreenUIState = tvShowScreenUIState.copy(tvShowList = tvShowsList, descriptionMinimised = updatedMap)
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
                    tvShowScreenUIState = tvShowScreenUIState.copy(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    tvShowScreenUIState = tvShowScreenUIState.copy(isLoading = true)
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