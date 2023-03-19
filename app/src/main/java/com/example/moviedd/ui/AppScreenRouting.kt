package com.example.moviedd.ui

sealed class AppScreenRouting(val title: String, val route: String) {
    object Home : AppScreenRouting("Home", "route")
}

private val screens = listOf(AppScreenRouting.Home)