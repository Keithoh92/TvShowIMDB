package com.example.moviedd.ui.tv.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.spacing8
import com.example.moviedd.ui.tv.event.TvShowScreenEvent
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.example.moviedd.ui.tv.view.components.tvShowListItemCardViewLayouts.TvShowVerticalCardView

@Composable
fun LazyVerticalColumnHolder(
    tvShowList: List<ShowInfo>,
    tvShowScreenUIState: TvShowScreenUIState,
    hideKeyboard: () -> Unit,
    onEvent: (BaseComposeEvent) -> Unit
) {

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = tvShowScreenUIState.shouldScrollToTop) {
        listState.animateScrollToItem(0)
        onEvent(TvShowScreenEvent.SetScrollToStopToFalse)
    }

    LaunchedEffect(key1 = tvShowScreenUIState.scrollToShowByID.first, block = {
        if (tvShowScreenUIState.scrollToShowByID.first) {
            listState.animateScrollToItem(tvShowScreenUIState.scrollToShowByID.second-1)
            onEvent(TvShowScreenEvent.SetScrollToIdToFalse)
            hideKeyboard.invoke()
        }
    })

    LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier.padding(spacing8)
        ) {
            items(tvShowList.size) { item ->
                TvShowVerticalCardView(tvShow = tvShowList[item], descriptionMinimised = tvShowScreenUIState.descriptionMinimised, onEvent = onEvent)
            }
        }
}