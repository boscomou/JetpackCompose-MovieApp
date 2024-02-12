package com.example.movieapptest.data.SentimantAnalysisApi.SentimentFunction

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.movieapptest.data.SentimantAnalysisApi.api.SentimentAnalysisApiConstants
import com.example.movieapptest.ui.ui.SentimentAnalysisScreen.SentimentAnalysisViewModel

fun classificSentiment(comments: List<HashMap<String, String>>,sentimentAnalysisViewModel:SentimentAnalysisViewModel,callback: ( ArrayList<String>)->Unit) {

    val stateSentimentAnalysis =  mutableStateOf(sentimentAnalysisViewModel.state.value)
    var getSentimentAnalysiss = arrayListOf<String>()



    for (comment in comments) {
        Log.v("zxcv comment", comment["comment"]!!)

            sentimentAnalysisViewModel.getSentimentAnalysis(
                SentimentAnalysisApiConstants.API_KEY, comment["comment"]!!
            ){}
                if ( stateSentimentAnalysis.value == null) {
                    Log.v("zxcv commentss", getSentimentAnalysiss.toString())
                } else {
                    if ( stateSentimentAnalysis.value!!.sentiment.positive >  stateSentimentAnalysis.value!!.sentiment.neutral && stateSentimentAnalysis.value!!.sentiment.positive > stateSentimentAnalysis.value!!.sentiment.negative) {
                        getSentimentAnalysiss.add("happy")
                        Log.v("zxcv commentss", getSentimentAnalysiss.toString())

                    } else if (stateSentimentAnalysis.value!!.sentiment.neutral > stateSentimentAnalysis.value!!.sentiment.positive && stateSentimentAnalysis.value!!.sentiment.neutral > stateSentimentAnalysis.value!!.sentiment.negative) {
                        getSentimentAnalysiss.add("neutral")
                        Log.v("zxcv commentss", getSentimentAnalysiss.toString())
                    } else {
                        getSentimentAnalysiss.add("sad")
                        Log.v("zxcv commentss", getSentimentAnalysiss.toString())
                    }
                }





        }
    callback(getSentimentAnalysiss)


    }
