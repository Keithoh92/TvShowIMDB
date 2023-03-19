package com.example.moviedd.data.api.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.moviedd.common.Resource
import com.example.moviedd.data.api.remote.TVShowsApi
import com.example.moviedd.domain.api.repository.TvShowApiRepo
import com.example.moviedd.domain.model.ShowInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class TvShowApiRepositoryImpl(
    private val tvShowsApi: TVShowsApi,
): TvShowApiRepo {

    companion object {
        const val API_KEY = "25a8f80ba018b52efb64f05140f6b43c"
    }

    override suspend fun downloadTvShows(): Flow<Resource<List<ShowInfo>>> = flow {
        try {
            emit(Resource.Loading())
            val response = tvShowsApi.getTopRatedTvShows(API_KEY)
            val responseBody = response.body()
            val apiResponseResults = responseBody?.results
            val showInfoList = apiResponseResults?.map { tvShow ->
                ShowInfo(id = 0, title = tvShow.title, imagePath = tvShow.imagePath, tvShow.airDate, tvShow.description, tvShow.rating)
            } ?: emptyList()

            emit(Resource.Success(showInfoList))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server, check internet connection: ${e.message}"))
            Log.e(TAG, "Couldn't reach server, check internet connection: ${e.message}")
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Http Exception, unexpected response: ${e.code()} ${e.message}"))
            Log.e(TAG, "Http Exception, unexpected response: ${e.code()} ${e.message}")
        }
    }
}