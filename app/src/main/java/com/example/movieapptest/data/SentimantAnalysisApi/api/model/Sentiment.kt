package com.example.movieapptest.data.SentimantAnalysisApi.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sentiment(
    @Json(name = "negative")
    val negative: Double,
    @Json(name = "neutral")
    val neutral: Double,
    @Json(name = "positive")
    val positive: Double
)