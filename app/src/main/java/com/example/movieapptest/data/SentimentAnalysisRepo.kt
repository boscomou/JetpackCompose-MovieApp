package com.example.movieapptest.data

import com.example.movieapptest.data.SentimantAnalysisApi.api.SentimentAnalysisApi
import com.example.movieapptest.data.SentimantAnalysisApi.api.model.SentimentAnalysis
import javax.inject.Inject

class SentimentAnalysisRepo @Inject constructor(
    private val sentimentAnalysisApi: SentimentAnalysisApi
    ) {
    suspend fun getSentimentAnalysis(api_key: String,text: String): SentimentAnalysis {

        return sentimentAnalysisApi.getSentimentAnalysis(api_key, text)
    }
}