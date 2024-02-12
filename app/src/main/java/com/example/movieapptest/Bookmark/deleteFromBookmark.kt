package com.example.movieapptest.Bookmark

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun deleteFromBookmark(movieID:String,mainScreenViewModel: MainScreenViewModel){

    val uId: MutableLiveData<String> = MutableLiveData()
    FirebaseAuth.getInstance().currentUser?.also { it.uid?.also{ uid-> uId.value = uid } }
    mainScreenViewModel.state.value.bookmark.remove(movieID.toString())
    val user = mainScreenViewModel.state.value
    Log.v("bookmark",mainScreenViewModel.state.value.bookmark.toString())
    val db = Firebase.firestore
    val userRef = db.collection("Users").document(uId.value!!)

    userRef.set(user)
}