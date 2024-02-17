package com.example.moviedd.ui.tv.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.moviedd.R
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.theme.fontSize20
import com.example.moviedd.ui.theme.spacing8
import com.example.moviedd.ui.tv.mockData.mockTvShowList
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.example.moviedd.ui.tv.view.components.SearchBar
import com.example.moviedd.ui.tv.view.components.TvShowList
import com.example.moviedd.ui.tv.view.components.TvShowLoadingDialog
import com.example.moviedd.ui.tv.view.components.TvShowScreenSortingBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TvShowScreen(
    tvShowScreenUIState: StateFlow<TvShowScreenUIState>,
    onEvent: (BaseComposeEvent) -> Unit
) {
    var hideKeyboard by remember { mutableStateOf(false) }
    val uiState by tvShowScreenUIState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing8)
            .clickable { hideKeyboard = true }
    ) {
        AnimatedVisibility(visible = uiState.isLoading && !uiState.isRefreshing) {
            TvShowLoadingDialog()
        }

        if (uiState.tvShowList.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    onEvent = onEvent,
                    suggestions = uiState.searchedTvShows,
                    hideKeyboard = hideKeyboard,
                    onFocusClear = { hideKeyboard = false }
                )
                Column(modifier = Modifier.padding(top = 80.dp)) {
                    TvShowScreenSortingBar(
                        isGridView = uiState.isGridView,
                        onEvent = onEvent
                    )

                    TvShowList(
                        tvShowScreenUIState = uiState,
                        onEvent = onEvent,
                        hideKeyboard = { hideKeyboard = true }
                    )
                }
            }
        }

        if (uiState.noTvShowsReturned) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val message = uiState.error.ifBlank {
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
            tvShowScreenUIState = MutableStateFlow(
                TvShowScreenUIState(
                    isLoading = false,
                    isRefreshing = false,
                    tvShowList = mockTvShowList(),
                    searchedTvShows = listOf(
                        Pair(1, "arcane"), Pair(2, "Avatar")
                    )
                )
            ),
            onEvent = {}
        )
    }
}