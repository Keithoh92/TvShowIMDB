package com.example.moviedd.ui.tv.state

import com.example.moviedd.domain.model.ShowInfo

data class TvShowScreenUIState(
    val tvShowList: List<ShowInfo> = emptyList(),
    val shouldScrollToTop: Boolean = false,
    val descriptionMinimised: Map<Int, Boolean> = mutableMapOf<Int, Boolean>().apply { put(-1, true) },
    val isGridView: Boolean = false,
    val noTvShowsReturned: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = true,
    val error: String = ""
)