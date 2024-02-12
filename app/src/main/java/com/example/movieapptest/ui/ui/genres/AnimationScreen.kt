package com.example.movieapptest.ui.ui.genres

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel
import com.example.movieapptest.ui.ui.popular.ClickableImageWithPopup

@Composable
fun AnimationScreen(
    navController: NavHostController,
    animationViewModel: AnimationViewModel = hiltViewModel(),
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val state by animationViewModel.state.collectAsState()
    var page by remember { mutableStateOf(1) }
    val scrollState = rememberScrollState()

    Column {
        animationViewModel.getActionMovies(ApiConstants.API_KEY, page.toString())
        Button(onClick = { navController.navigate("MainScreen") }) {
            Text(text = "<Back")
        }


        Box(modifier = Modifier.height(650.dp)) {
            LazyColumn(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .sizeIn(maxHeight = 800.dp)
            ) {
                if (state == null) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.wrapContentSize(),
                            )
                        }
                    }
                } else {

                    item {
                        val animationMovieArray = state!!.results
                        animationMovieArray?.chunked(2)?.forEach { rowMovies ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowMovies.forEach { movieModel ->
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp)
                                            .height(380.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        ClickableImageWithPopup(movieModel, mainScreenViewModel )
                                    }
                                }
                            }
                        }

                    }
                }


            }

        }
        Box {
            Row(
                modifier = Modifier
// .background(backgroundColor)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {

                Button(onClick = {
                    page += 1

                }) {
                    Text("NextPage$page")
                    Log.d("Tagg", "pagestext=$page")
                    Log.d("Tagg", "pages1=$page")


                }
            }
        }
    }
}

