package com.example.moviedd

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.moviedd.ui.Home
import com.example.moviedd.ui.tv.viewModel.TvShowScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvShowScreenViewModel: TvShowScreenViewModel by viewModels()

        setContent {
            Home(
                viewModel = tvShowScreenViewModel,
                onEvent = tvShowScreenViewModel::onEvent
            )
        }
    }
}