package com.example.movieapptest.ui.ui.SentimentAnalysisScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapptest.data.SentimantAnalysisApi.api.model.Sentiment
import com.example.movieapptest.data.SentimantAnalysisApi.api.model.SentimentAnalysis
import com.example.movieapptest.data.SentimentAnalysisRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltViewModel
class SentimentAnalysisViewModel @Inject constructor(
    private val sentimentAnalysisRepo: SentimentAnalysisRepo
) : ViewModel() {
    private val _state = MutableStateFlow<SentimentAnalysis?>(null)
    val state: StateFlow<SentimentAnalysis?>
        get() = _state

    fun getSentimentAnalysis(apiKey: String, text: String,callback: (Sentiment)->Unit){
        Log.v("zxcv commentInViewModel",text)
        viewModelScope.launch {
            _state.value = null

            var sentimentAnalysis: SentimentAnalysis? = null
            var retryCount = 0
            val maxRetries = 3
            val retryIntervalMillis = 500L

            while (sentimentAnalysis == null && retryCount < maxRetries) {
                withTimeoutOrNull(2000) {
                    try {
                        sentimentAnalysis = sentimentAnalysisRepo.getSentimentAnalysis(apiKey, text)
                    } catch (e: Exception) {
                        Log.e("SentimentAnalysis", "Error: ${e.message}")
                    }
                }

                if (sentimentAnalysis == null) {
                    retryCount++

                }
            }

            _state.value = sentimentAnalysis
            _state.value?.sentiment?.let(callback)

            Log.v("zxcv commentInViewModelReturn", _state.value!!.sentiment.toString())

        }

    }
}