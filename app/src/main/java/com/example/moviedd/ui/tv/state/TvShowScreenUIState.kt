package com.example.moviedd.ui.tv.state

import com.example.moviedd.domain.model.ShowInfo

data class TvShowScreenUIState(
    val tvShowList: List<ShowInfo> = emptyList(),
    val shouldScrollToTop: Boolean = false,
    val scrollToShowByID: Pair<Boolean, Int> = Pair(false, -1),
    val descriptionMinimised: Map<Int, Boolean> = mutableMapOf<Int, Boolean>().apply { put(-1, true) },
    val isGridView: Boolean = false,
    val noTvShowsReturned: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = true,
    val error: String = "",
    val searchedTvShows: List<Pair<Int, String>> = emptyList()
)