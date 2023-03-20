package com.example.moviedd.mock

import com.example.moviedd.data.api.dao.ShowDao
import com.example.moviedd.data.database.entity.TVShow
import com.example.moviedd.domain.model.ShowInfo

fun mockTvShowList(): List<TVShow> {
    val tvShowList = mutableListOf<TVShow>()
    for (i in 0..3) {
        tvShowList.add(
            TVShow(
                id = i,
                title = "Testing TvShow $i",
                imagePath = "pathToImage$i",
                description = "Tv show description $i",
                rating = 7.5,
                airDate = "10-02-2023"
            )
        )
    }

    return tvShowList
}

fun mockShowDaoList(): List<ShowDao> {
    val showDaoList = mutableListOf<ShowDao>()

    for (i in 0..3) {
        showDaoList.add(
            ShowDao(
                title = "Testing TvShow $i",
                imagePath = "pathToImage$i",
                description = "Tv show description $i",
                rating = 7.5,
                airDate = "10-02-2023"
            )
        )
    }

    return showDaoList
}

fun mockShowInfoList(): List<ShowInfo> {

    val tvShows = mutableListOf<ShowInfo>()

    val tvShowTitles = listOf("C TvShow", "A TvShow")
    val tvShowAirDates = listOf("10-01-22", "09-01-22")
    val tvShowRatings = listOf(8.9, 9.9)

    for (i in 0..1) {
        tvShows.add(
            ShowInfo(
                id = i,
                title = tvShowTitles[i],
                imagePath = "pathToImage",
                airDate = tvShowAirDates[i],
                description = "This is a great tv show",
                rating = tvShowRatings[i]
            )
        )
    }

    return tvShows
}

fun mockDescriptionMinimisedMap(): Map<Int, Boolean> {
    val mockMap = mutableMapOf<Int, Boolean>()
    mockMap[0] = true
    mockMap[1] = true

    return mockMap
}