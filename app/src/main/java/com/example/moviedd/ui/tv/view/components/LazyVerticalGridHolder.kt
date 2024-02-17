package com.example.moviedd.ui.tv.view.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.tv.event.TvShowScreenEvent
import com.example.moviedd.ui.tv.mockData.mockTvShowList
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.example.moviedd.ui.tv.view.components.tvShowListItemCardViewLayouts.TvShowGridCardView

@Composable
fun LazyVerticalGridHolder(
    tvShowList: List<ShowInfo>,
    tvShowScreenUIState: TvShowScreenUIState,
    hideKeyboard: () -> Unit,
    onEvent: (BaseComposeEvent) -> Unit
) {
    val listState = rememberLazyGridState()

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

    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2)
    ) {
        items(tvShowList.size) { item ->
            TvShowGridCardView(tvShow = tvShowList[item], descriptionMinimised = tvShowScreenUIState.descriptionMinimised, onEvent = onEvent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LazyVerticalGridCardViewPreview() {
    AppTheme {
        LazyVerticalGridHolder(
            tvShowList = mockTvShowList(),
            tvShowScreenUIState = TvShowScreenUIState(),
            onEvent = {},
            hideKeyboard = {}
        )
    }
}