package com.example.movieapptest.data.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "page")
    val page: Int? = null,
    @Json(name = "results")
    val results: List<Result>? = null,
    @Json(name = "total_pages")
    val totalPages: Int? = null,
    @Json(name = "total_results")
    val totalResults: Int? = null
)