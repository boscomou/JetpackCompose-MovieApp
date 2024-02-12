package com.example.movieapptest.movieCommentary

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.movieapptest.R
import com.example.movieapptest.Util.StorageUtil
import com.example.movieapptest.data.SentimantAnalysisApi.api.SentimentAnalysisApiConstants
import com.example.movieapptest.data.SentimantAnalysisApi.api.model.Sentiment
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel
import com.example.movieapptest.ui.ui.SentimentAnalysisScreen.SentimentAnalysisViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AddComment(title : String,mainScreenViewModel: MainScreenViewModel = hiltViewModel(),sentimentAnalysisViewModel: SentimentAnalysisViewModel=hiltViewModel()) {

    var postComment by remember { mutableStateOf("") }
    var getComment by remember { mutableStateOf(ArrayList<String>()) }
    var getFirstName by remember { mutableStateOf(ArrayList<String>()) }
    var getPhotoUri by remember { mutableStateOf(ArrayList<String>()) }
    var getSentimentAnalysis by remember { mutableStateOf(ArrayList<String>()) }
    var refresh by remember { mutableStateOf(0) }
    var firstName by remember { mutableStateOf("") }
    var storageUri by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf("") }
    var stateSentimentAnalysis  by remember { mutableStateOf(Sentiment(0.0,0.0,0.0)) }
    var classificSentiment by remember{mutableStateOf("")}



    Box(Modifier.padding(10.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = postComment,
            onValueChange = { postComment = it },
            label = { Text(text = "Comment") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done // Set the keyboard action to Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { refresh += 1 } // Increment refresh when the Done button is clicked
            )

        )
    }

    Button(onClick = {
        refresh += 1
        sentimentAnalysisViewModel.getSentimentAnalysis(SentimentAnalysisApiConstants.API_KEY,postComment){
            stateSentimentAnalysis = it
            Log.v("zxcv callback", stateSentimentAnalysis.toString())
        }

    }) {
        Text(text = "Post")

    }

    /*getComment(title){it->
        getComment = it


    }*/






    LaunchedEffect(
        refresh,stateSentimentAnalysis

    ) {
        //  Log.v("asdf state", stateSentimentAnalysis?.sentiment.toString())
        Log.v("zxcv stateSen", stateSentimentAnalysis.toString())
            classificSentiment = classificCommentSentiment(stateSentimentAnalysis)
            getUserName(mainScreenViewModel) {
                firstName = it
            }
            getStorageUri(mainScreenViewModel) {
                storageUri = it
                getUserPhotoUri(storageUri) {
                    photoUri = it.toString()
                }


            }
            Log.v("firstName", firstName)
            Log.v("refresh", refresh.toString())
            postComment(title, postComment, refresh, firstName, photoUri, classificSentiment) {
                getComment(title) { comments ->
                    var getComments = arrayListOf<String>()
                    for (comment in comments) {
                        Log.v("getc", comment["comment"]!!)
                        //getComment.add(comment["comment"]!!)
                        getComments.add(comment["comment"]!!)

                    }
                    getComment = getComments
                    var getFirstNames = arrayListOf<String>()
                    for (comment in comments) {
                        Log.v("getFirstName", comment["userFirstName"]!!)
                        //getComment.add(comment["comment"]!!)
                        getFirstNames.add(comment["userFirstName"]!!)

                    }
                    getFirstName = getFirstNames

                    var getPhotoUris = arrayListOf<String>()
                    for (comment in comments) {
                        Log.v("getPhotoUri", comment["userPhotoUri"]!!)
                        //getComment.add(comment["comment"]!!)
                        getPhotoUris.add(comment["userPhotoUri"]!!)

                    }
                    getPhotoUri = getPhotoUris

                    var getSentimentAnalysiss =   arrayListOf<String>()
                    for (comment in comments) {
                        Log.v("getSentimentAnalysis", comment["classificSentiment"]!!)
                        //getComment.add(comment["comment"]!!)
                        getSentimentAnalysiss.add(comment["classificSentiment"]!!)

                    }
                    getSentimentAnalysis = getSentimentAnalysiss


                }

            }
        stateSentimentAnalysis= Sentiment(0.0,0.0,0.0)



    }




    LazyColumn(modifier = Modifier.heightIn(0.dp, 2000.dp)) {



            var recieveClass = getComment.mapIndexed { index, content ->

                        RecieveMovieClass(
                            recievedUserFirstName = getFirstName[index],
                            recievedUserPhotoUri = getPhotoUri[index],
                            recievedUSerComments = getComment[index],
                            recievedUserCommentsSentimentAnalysis = getSentimentAnalysis[index]

                        )
                    }



            val paddingModifier = Modifier.padding(10.dp)
            items(recieveClass.reversed()) { recieveClass ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(70.dp)
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(
                            Modifier
                                .width(50.dp)
                                .height(50.dp)
                        ) {
                            Log.v("vbnm", "refresh")

                            var painter =
                                rememberImagePainter(recieveClass.recievedUserPhotoUri.toUri())
                            Card(
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(100.dp)
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(),

                                    contentScale = ContentScale.Crop
                                )
                            }
                        }


                        Column(Modifier.width(150.dp)) {


                            Text(
                                text = recieveClass.recievedUserFirstName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(text = recieveClass.recievedUSerComments)

                        }
                        Column(Modifier.fillMaxWidth().fillMaxHeight(), verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            if (recieveClass.recievedUserCommentsSentimentAnalysis == "happy") {
                                Image(
                                    painterResource(id = R.drawable.happyface),
                                    "happyface"
                                )
                            } else if (recieveClass.recievedUserCommentsSentimentAnalysis == "neutral") {
                                Image(
                                    painterResource(id = R.drawable.neutralface),
                                    "neutralface"
                                )

                            } else {
                                Image(
                                    painterResource(id = R.drawable.sadface),
                                    "sadface"
                                )
                            }
                        }

                    }
                }


                    Spacer(modifier = Modifier.height(6.dp))
                }
                postComment = ""
            }


    }



fun getUserName(mainScreenViewModel: MainScreenViewModel,callback: (String) -> Unit){
    mainScreenViewModel.getUserData()
    callback(mainScreenViewModel.state.value.firstName)

}

fun getStorageUri(mainScreenViewModel: MainScreenViewModel, callback: (String) -> Unit){
    mainScreenViewModel.getUserData()
    callback(mainScreenViewModel.state.value.photoUrl)

}
fun getUserPhotoUri(storageUri: String, callback: (Uri?) -> Unit) {
    StorageUtil.getPhotoFromStorage(storageUri) { Uri ->

        callback(Uri)
    }
}


fun postComment(title:String,comment:String,refresh:Int,firstName:String,photoUri:String,classificSentiment:String,callback: (Int) -> Unit) {
    if (refresh > 0 && classificSentiment!= "null") {

        val movieComment = MovieCommentary(firstName,photoUri,comment,classificSentiment)
        val db = Firebase.firestore
        val movieRef = db.collection("Movies").document(title)

        movieRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                Log.v("success","success")
                movieRef.update("comments", FieldValue.arrayUnion(movieComment))
                callback(1)
            }
            else{
                Log.v("fail","fail")
                val data = hashMapOf(
                    "comments" to FieldValue.arrayUnion(movieComment)
                )
                movieRef.set(data)
                callback(1)
            }

        }.addOnFailureListener {

        }


    }
    else{
        callback(1)
    }
}

fun getComment(title: String,callback:( List<HashMap<String, String>>)->Unit) {
    val db = FirebaseFirestore.getInstance()

    db.collection("Movies").document(title)
        .get()
        .addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val movie = documentSnapshot.toObject(getMovieCommentary::class.java)
                val comments = movie?.comments
                Log.v("comment",comments.toString())

                if (comments != null) {
                    // Process the comments data here
                    callback(comments)

                } else {

                }
            } else {

            }
        }
        .addOnFailureListener { e ->
            println("Error getting movie comments: ${e.message}")
        }
}