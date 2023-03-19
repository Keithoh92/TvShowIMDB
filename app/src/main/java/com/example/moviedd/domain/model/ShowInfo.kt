package com.example.moviedd.domain.model

data class ShowInfo(
    val id: Int,
    val title: String,
    val imagePath: String,
    val airDate: String,
    val description: String,
    val rating: Double
)