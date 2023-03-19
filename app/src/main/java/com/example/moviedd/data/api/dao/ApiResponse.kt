package com.example.moviedd.data.api.dao

data class ApiResponse(
    val page: Int,
    val results: List<ShowDao>,
    val total_pages: Int,
    val total_results: Int
)
