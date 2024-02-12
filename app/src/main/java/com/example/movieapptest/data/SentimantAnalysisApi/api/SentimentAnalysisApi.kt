package com.example.movieapptest.data.SentimantAnalysisApi.api

import com.example.movieapptest.data.SentimantAnalysisApi.api.model.SentimentAnalysis
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SentimentAnalysisApi {

    @POST("/v4/sentiment")
    @FormUrlEncoded
    suspend fun getSentimentAnalysis(
        @Field("api_key") apiKey: String,
        @Field("text") text: String
    ): SentimentAnalysis
}