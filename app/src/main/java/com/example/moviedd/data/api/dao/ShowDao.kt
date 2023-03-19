package com.example.moviedd.data.api.dao

import com.google.gson.annotations.SerializedName

data class ShowDao(
    @SerializedName("name")
    val title: String,
    @SerializedName("poster_path")
    val imagePath: String,
    @SerializedName("first_air_date")
    val airDate: String,
    @SerializedName("overview")
    val description: String,
    @SerializedName("vote_average")
    val rating: Double
)