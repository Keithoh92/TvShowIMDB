package com.example.moviedd.ui

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.tv.state.TvShowScreenUIState
import com.example.moviedd.ui.tv.view.TvShowScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    tvShowScreenUIState: TvShowScreenUIState,
    onEvent: (BaseComposeEvent) -> Unit
) {
    val navController = rememberNavController()

    AppTheme {
        Scaffold(
            topBar = { TopAppBar() },
            content = {
                NavHost(
                    navController = navController,
                    startDestination = AppScreenRouting.Home.route
                ) {
                    composable(AppScreenRouting.Home.route) {
                        TvShowScreen(
                            tvShowScreenUIState = tvShowScreenUIState,
                            onEvent = onEvent
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    AppTheme {
        Home(
            tvShowScreenUIState = TvShowScreenUIState(),
            onEvent = {}
        )
    }
}