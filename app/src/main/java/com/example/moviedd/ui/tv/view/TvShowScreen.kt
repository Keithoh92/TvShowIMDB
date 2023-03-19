package com.example.moviedd.ui.tv.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviedd.R
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.theme.fontSize20
import com.example.moviedd.ui.theme.spacing8
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.example.moviedd.ui.tv.view.components.TvShowList
import com.example.moviedd.ui.tv.view.components.TvShowLoadingDialog
import com.example.moviedd.ui.tv.view.components.TvShowScreenSortingBar

@Composable
fun TvShowScreen(
    tvShowScreenUIState: TvShowScreenUIState,
    onEvent: (BaseComposeEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing8)
    ) {

        AnimatedVisibility(visible = tvShowScreenUIState.isLoading && !tvShowScreenUIState.isRefreshing) {
            TvShowLoadingDialog()
        }

        if (tvShowScreenUIState.tvShowList.isNotEmpty()) {

            Column {
                TvShowScreenSortingBar(
                    isGridView = tvShowScreenUIState.isGridView,
                    onEvent = onEvent
                )

                TvShowList(
                    tvShowScreenUIState = tvShowScreenUIState,
                    onEvent = onEvent
                )
            }
        }

        if (tvShowScreenUIState.noTvShowsReturned) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val message = tvShowScreenUIState.error.ifBlank {
                    stringResource(R.string.tv_show_list_not_available)
                }
                Text(
                    text = message,
                    fontSize = fontSize20,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TvShowScreenPreview() {
    AppTheme {
        TvShowScreen(
            tvShowScreenUIState = TvShowScreenUIState(),
            onEvent = {}
        )
    }
}