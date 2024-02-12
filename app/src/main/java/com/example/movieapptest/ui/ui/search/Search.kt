package com.example.movieapptest.ui.ui.search

import android.annotation.SuppressLint
import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.movieapptest.R
import com.example.movieapptest.data.api.ApiConstants
import com.example.movieapptest.data.api.model.Result

@SuppressLint("SuspiciousIndentation")
@Composable
fun Search(query: String, searchViewModel: SearchViewModel = hiltViewModel()) {

    var page: Int by remember { mutableStateOf(1) }
    val state by searchViewModel.state.collectAsState()

    searchViewModel.searchMovies(ApiConstants.API_KEY, query, page.toString())

    Column {
        Box(modifier = Modifier.background(color = Color.White)) {
            Text(
                text = "Search: $query",
                fontSize = 25.sp,
                style = MaterialTheme.typography.subtitle1,
            )
        }

        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .width(500.dp)
                .height(600.dp)
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
                    state!!.results?.forEach { movieModel ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            ClickableImageWithPopUpSearch(movieModel)

                        }
                        Spacer(
                            modifier = Modifier
                                .height(16.dp)
                        )
                    }

                }
            }


        }
        Row(
            modifier = Modifier
// .background(backgroundColor)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = {
                page -= 1

            }) {
                Text("<")
            }

            Text(text = "$page", fontSize = 30.sp)

            Button(onClick = {
                page += 1

            }) {
                Text(">")
            }
        }
    }
}

@Composable
fun ClickableImageWithPopUpSearch(movieModel: Result) {
    val scrollState = rememberScrollState()
    var showPopup by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .height(500.dp)
            .width(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color(255, 255, 255))
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500" + movieModel.posterPath),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()

                .clickable {
                    showPopup = true
                    movieModel.title?.let { Log.v("Click", it) }
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), contentAlignment = Alignment.Center
        ) {
            movieModel.title?.let { Text(text = it, textAlign = TextAlign.Center) }
        }
        Spacer(modifier = Modifier.height(5.dp))

        movieModel.voteAverage?.div(2f)?.let { RatingBar(rating = it) }
    }

    if (showPopup) {
        Dialog(
            onDismissRequest = { showPopup = false },
            content = {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .height(400.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Blue)
                ) {
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
                                .padding(8.dp)
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
                                    .padding(start = 4.dp, end = 16.dp, bottom = 16.dp),
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
            }
        )
    }
}

@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    spaceBetween: Dp = 0.dp
) {

    val image = ImageBitmap.imageResource(id = R.drawable.star_background)
    val imageFull = ImageBitmap.imageResource(id = R.drawable.star_foreground)

    val totalCount = 5

    val height = LocalDensity.current.run { image.height.toDp() }
    val width = LocalDensity.current.run { image.width.toDp() }
    val space = LocalDensity.current.run { spaceBetween.toPx() }
    val totalWidth = width * totalCount + spaceBetween * (totalCount - 1)


    Box(
        modifier
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRating(rating, image, imageFull, space)
            })
}

private fun DrawScope.drawRating(
    rating: Float,
    image: ImageBitmap,
    imageFull: ImageBitmap,
    space: Float
) {

    val totalCount = 5

    val imageWidth = image.width.toFloat()
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until totalCount) {

        val start = imageWidth * i + space * i

        drawImage(
            image = image,
            topLeft = Offset(start, 0f)
        )
    }

    drawWithLayer {
        for (i in 0 until totalCount) {
            val start = imageWidth * i + space * i
            // Destination
            drawImage(
                image = imageFull,
                topLeft = Offset(start, 0f)
            )
        }

        val end = imageWidth * totalCount + space * (totalCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val size = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

