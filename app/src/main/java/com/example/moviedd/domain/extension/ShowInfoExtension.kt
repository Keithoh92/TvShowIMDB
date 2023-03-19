package com.example.moviedd.domain.extension

import com.example.moviedd.data.api.dao.ShowDao
import com.example.moviedd.data.database.entity.TVShow
import com.example.moviedd.domain.model.ShowInfo

fun ShowDao.toShowInfo(): ShowInfo {
    return ShowInfo(
        id = 0,
        title = this.title,
        imagePath = this.imagePath,
        airDate = this.airDate,
        description = this.description,
        rating = this.rating
    )
}

fun TVShow.toShowInfo(): ShowInfo {
    return ShowInfo(
        id = this.id,
        title = this.title,
        imagePath = this.imagePath,
        airDate = this.airDate,
        description = this.description,
        rating = this.rating
    )
}

fun ShowInfo.toTVShow(): TVShow {
    return TVShow(
        id = 0,
        title = this.title,
        imagePath = this.imagePath,
        airDate = this.airDate,
        description = this.description,
        rating = this.rating
    )
}