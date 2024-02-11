package com.example.moviedd.ui.tv.event

import com.example.moviedd.ui.event.BaseComposeEvent

sealed class TvShowScreenEvent : BaseComposeEvent {
    data class OnSortOptionChosen(val sortingOption: String) : TvShowScreenEvent()
    data class OnDescriptionClicked(val showId: Int, val isMinimised: Boolean?) : TvShowScreenEvent()
    object SetScrollToStopToFalse : TvShowScreenEvent()
    object OnRefresh : TvShowScreenEvent()
    data class OnViewChanged(val isGridView: Boolean) : TvShowScreenEvent()
    data class OnSearchTextChanged(val prefix: String) : TvShowScreenEvent()
    data class OnSelectSearchedTvShow(val title: String): TvShowScreenEvent()
}
