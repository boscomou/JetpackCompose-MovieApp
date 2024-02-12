package com.example.movieapptest.Bookmark

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieapptest.data.api.ApiConstants
import com.example.movieapptest.data.api.model.Result
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel




@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BookmarkScreen(navController: NavHostController,bookMarkViewModel: BookMarkViewModel= hiltViewModel(),mainScreenViewModel: MainScreenViewModel= hiltViewModel()) {
    mainScreenViewModel.getDataFromFirebase(){}
    val state by bookMarkViewModel.state.collectAsState()
    val movieIdList = mainScreenViewModel.state.value.bookmark
    val movieList by remember { mutableStateOf(ArrayList<Result>()) }
    val scrollState = rememberScrollState()
    var currentIndex by remember {
        mutableStateOf(0)
    }
    var finishedRunning by remember {
        mutableStateOf(true)
    }



    Log.v("size", movieIdList.size.toString())


    if (currentIndex < movieIdList.size && finishedRunning) {
        finishedRunning=false
        val id = movieIdList[currentIndex]
        Log.v("qwerty movieID", id)

        bookMarkViewModel.getIDMovies(ApiConstants.API_KEY, id) { states ->
            Log.v("qwerty movie id", "state: $state")
            states?.let { it1 ->
                Log.v("qwerty final movie", it1.originalTitle.toString())
                movieList.add(it1)
                currentIndex++
                finishedRunning=true
            }

            // Process the next movie ID after the current one is finished


        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Log.v("qwert size", movieList.size.toString())
        movieList.forEach() {
            Log.v("qwert ggfr", it.originalTitle.toString())

            Box(
                modifier = Modifier
                    .height(370.dp)
                    .width(200.dp),
                contentAlignment = Alignment.Center
            ) {
                ClickableImageWithPopup(movieModel = it, mainScreenViewModel = mainScreenViewModel){it->
                    if(it == true){
                        movieList.clear()
                        currentIndex=0
                    }

                }
            }
            Spacer(Modifier.height(20.dp))

        }
        //movieList.clear()
    }

}



