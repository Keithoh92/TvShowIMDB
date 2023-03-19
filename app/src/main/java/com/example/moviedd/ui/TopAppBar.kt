package com.example.moviedd.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviedd.R
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.AppTheme
import com.example.moviedd.ui.tv.event.TvShowScreenEvent

@Composable
fun TopAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_title)) },
        backgroundColor = colorResource(id = R.color.dark_grey_app),
        elevation = 10.dp,
    )
}

@Preview
@Composable
fun TopAppBarPreview() {
    AppTheme {
        TopAppBar()
    }
}