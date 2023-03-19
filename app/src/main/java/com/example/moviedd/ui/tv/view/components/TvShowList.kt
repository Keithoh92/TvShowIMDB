package com.example.moviedd.ui.tv.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.tv.event.TvShowScreenEvent
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun TvShowList(
    tvShowScreenUIState: TvShowScreenUIState,
    onEvent: (BaseComposeEvent) -> Unit
) {
    val tvShowList = tvShowScreenUIState.tvShowList
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = tvShowScreenUIState.isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        swipeEnabled = true,
        onRefresh = { onEvent(TvShowScreenEvent.OnRefresh) }
    ) {
        if (tvShowScreenUIState.isGridView) {
            LazyVerticalGridHolder(tvShowList = tvShowList, tvShowScreenUIState = tvShowScreenUIState, onEvent = onEvent)
        } else {
            LazyVerticalColumnHolder(tvShowList = tvShowList, tvShowScreenUIState = tvShowScreenUIState, onEvent = onEvent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TvShowListPreview() {
    AppTheme {
        TvShowList(
            tvShowScreenUIState = TvShowScreenUIState(),
            onEvent = {}
        )
    }
}