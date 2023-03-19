package com.example.moviedd.data.api.remote

import com.example.moviedd.data.api.dao.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TVShowsApi {

    @GET("/3/tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<ApiResponse>
}