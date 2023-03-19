package com.example.moviedd.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.example.moviedd.R

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val darkColorPalette = darkColors(
        primary = colorResource(id = R.color.dark_grey_app),
        primaryVariant = colorResource(id = R.color.light_grey_app),
        secondary = colorResource(id = R.color.off_white_app)
    )

    val lightColorPalette = lightColors(
        primary = colorResource(id = R.color.dark_grey_app),
        primaryVariant = colorResource(id = R.color.off_white_app),
        secondary = colorResource(id = R.color.red_app)
    )

    val colors = if (darkTheme) { darkColorPalette } else { lightColorPalette }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}