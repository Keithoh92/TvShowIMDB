package com.example.moviedd.ui.tv.mockData

import com.example.moviedd.domain.model.ShowInfo

fun mockTvShowList(): List<ShowInfo> {

    val tvShows = mutableListOf<ShowInfo>()

    for (i in 0..5) {
        tvShows.add(
            ShowInfo(
                id = i,
                title = "TV Show $i",
                imagePath = "pathToImage",
                airDate = "10-01-22",
                description = "This is a great tv show",
                rating = 8.9
            )
        )
    }

    return tvShows
}