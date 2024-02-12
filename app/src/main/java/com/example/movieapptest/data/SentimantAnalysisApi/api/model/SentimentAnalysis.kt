package com.example.movieapptest.data.SentimantAnalysisApi.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SentimentAnalysis(
    @Json(name = "sentiment")
    val sentiment: Sentiment
)