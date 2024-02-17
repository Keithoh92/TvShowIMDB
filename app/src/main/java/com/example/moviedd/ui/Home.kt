package com.example.moviedd.ui

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.tv.view.TvShowScreen
import com.example.moviedd.ui.tv.viewModel.TvShowScreenViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    viewModel: TvShowScreenViewModel,
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
                            tvShowScreenUIState = viewModel.uiState,
                            onEvent = onEvent
                        )
                    }
                }
            }
        )
    }
}