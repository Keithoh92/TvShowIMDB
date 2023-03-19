package com.example.moviedd.domain.api.repository

import com.example.moviedd.common.Resource
import com.example.moviedd.domain.model.ShowInfo
import kotlinx.coroutines.flow.Flow

interface TvShowApiRepo {

    suspend fun downloadTvShows(): Flow<Resource<List<ShowInfo>>>
}