package com.example.moviedd.domain.database.repository

import com.example.moviedd.domain.model.ShowInfo

interface TvShowDBRepo {

    suspend fun getTvShows(): List<ShowInfo>

    suspend fun insert(showInfo: ShowInfo)
}