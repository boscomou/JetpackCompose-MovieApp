package com.example.movieapptest.ui.ui.SentimentAnalysisScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapptest.data.SentimantAnalysisApi.api.SentimentAnalysisApiConstants

@Composable
fun SentimentAnalysisScreen(navController: NavHostController,sentimentAnalysisViewModel: SentimentAnalysisViewModel= hiltViewModel()){
    var text by remember{ mutableStateOf("") }
    val state by sentimentAnalysisViewModel.state.collectAsState()

    Column(){
        Row() {
            TextField(value = text, onValueChange = { text = it })
            Button( onClick = { sentimentAnalysisViewModel.getSentimentAnalysis(SentimentAnalysisApiConstants.API_KEY,text){} }){
                Text(text="send")

            }
        }
        Text(text=text)

        if (state == null) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )

        } else {
                Text(text = state!!.sentiment.toString())


            }
    }

}