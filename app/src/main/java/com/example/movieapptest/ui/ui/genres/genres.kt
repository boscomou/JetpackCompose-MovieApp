package com.example.movieapptest.ui.ui.genres

import android.util.Log
import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapptest.data.api.model.Result
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@Composable
fun ActionMovies(
    navController: NavController,
    actionViewModel: ActionViewModel = hiltViewModel()
) {

    val state by actionViewModel.state.collectAsState()

    Column(modifier = Modifier.height(400.dp)) {

        Row(modifier = Modifier.padding(4.dp)) {
            Button(onClick = {
                navController.navigate("ActionScreen")
            }) {
                Text(text = "Action")

            }
        }

        Box(modifier = Modifier.height(650.dp)) {
            LazyColumn(
                modifier = Modifier

                    .sizeIn(maxHeight = 800.dp)
            ) {
                if (state == null) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                } else {

                    item {
                        val actionMovieArray = state!!.results
                        if (actionMovieArray != null) {
                            MovieBanner(actionMovieArray)
                        }

                    }

                }
            }


        }

    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MovieBanner(genresMovieArrayToSet: List<Result>) {
    val pagerState = rememberPagerState()
    val bannerIndex = remember { mutableStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerIndex.value = page
        }
    }

    //auto scroll
    LaunchedEffect(pagerState) {
        while (true) {
            delay(2_000)
            tween<Float>(1000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount
            )

        }
    }

    Log.v("df", genresMovieArrayToSet.size.toString())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = genresMovieArrayToSet.size,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) { index ->

            Image(
                painter = rememberAsyncImagePainter(
                    "https://image.tmdb.org/t/p/w500" + genresMovieArrayToSet.elementAt(
                        index
                    ).posterPath
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .fillMaxSize()
                /*.clickable {
                    showPopup = true
                    Log.v("Click", movieModel.title)
                }*/
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            repeat(genresMovieArrayToSet.size) { index ->
                val height = 12.dp
                val width = if (index == bannerIndex.value) 28.dp else 12.dp
                val color = if (index == bannerIndex.value) Color.Yellow else Color.Gray
                Surface(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(width, height)
                        .clip(RoundedCornerShape(20.dp)),
                    color = color
                ) {

                }

            }

        }

    }


}

@Composable
fun AnimationMovie(
    navController: NavController,
    animationViewModel: AnimationViewModel = hiltViewModel()
) {
    val state by animationViewModel.state.collectAsState()


    Column(modifier = Modifier.height(500.dp)) {

        Row(modifier = Modifier.padding(4.dp)) {
            Button(onClick = {
                navController.navigate("AnimationScreen")
            }) {
                Text(text = "Animation")

            }
        }

        Box(modifier = Modifier.height(500.dp)) {
            LazyColumn(
                modifier = Modifier

                    .height(600.dp)
                    .sizeIn(maxHeight = 600.dp),
                userScrollEnabled = false
            ) {
                if (state == null) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                } else {

                    item {
                        val animationMovieArray = state!!.results
                        if (animationMovieArray != null) {
                            HorizontalPager(animationMovieArray)
                        }

                    }

                }
            }


        }

    }
}

@Composable
fun HorizontalPager(movieArray: List<Result>) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.horizontalScroll(state = scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)

        ) {
            movieArray.forEach { movieModel ->
                Column(
                    Modifier
                        .height(300.dp)
                        .width(200.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(200.dp)
                            .height(20.dp), contentAlignment = Alignment.Center
                    ) {
                        movieModel.title?.let { Text(text = it, textAlign = TextAlign.Start) }

                    }
                    ClickableImageWithPopupGenres(movieModel)

                }
            }
        }
    }
}


@Composable
fun ClickableImageWithPopupGenres(movieModel: Result) {
    val scrollState = rememberScrollState()
    var showPopup by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500" + movieModel.posterPath),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .fillMaxSize()
                .clickable {
                    showPopup = true
                    movieModel.title?.let { Log.v("Click", it) }
                }
        )
        Spacer(modifier = Modifier.height(5.dp))
    }

    if (showPopup) {
        Dialog(
            onDismissRequest = { showPopup = false },
            content = {
                Column(
                    Modifier
                        .background(Color.White)
                        .width(500.dp)
                        .height(400.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500" + movieModel.posterPath),
                        contentDescription = null,
                        Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .aspectRatio(2f / 3f)
                            .scale(1f)
                    )

                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .width(500.dp)
                            .height(250.dp)
                            .verticalScroll(scrollState)
                            .weight(1f)
                    ) {
                        movieModel.overview?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Rating:",
                                style = MaterialTheme.typography.subtitle1
                            )

                            AndroidView(
                                factory = { context ->
                                    RatingBar(context).apply {
                                        max = 5
                                    }
                                },
                                update = { ratingBar ->
                                    ratingBar.rating =
                                        (movieModel.voteAverage?.div(2f) ?: 0) as Float
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}
