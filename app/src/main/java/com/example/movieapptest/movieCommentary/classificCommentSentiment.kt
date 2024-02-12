package com.example.movieapptest.movieCommentary

import android.util.Log
import com.example.movieapptest.data.SentimantAnalysisApi.api.model.Sentiment

fun classificCommentSentiment(sentiment: Sentiment):String{
    Log.v("zxcv class", sentiment.toString())

        if(sentiment.positive == 0.0 &&  sentiment.neutral == 0.0 && sentiment.negative== 0.0){
            return "null"
        }
        else if ( sentiment.positive >  sentiment.neutral && sentiment.positive >sentiment.negative) {
            Log.v("zxcv class","happy")
            return "happy"


        } else if (sentiment.neutral > sentiment.positive && sentiment.neutral > sentiment.negative) {
            Log.v("zxcv class","neutral")
            return "neutral"

        } else {
            Log.v("zxcv class","sad")
            return "sad"

        }
}