package com.example.movieapptest.ui.ui.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.RegistrationAndLogin.signup.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {
    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Profile",
            icon = Icons.Default.Person,
            description = "Profile",
            itemId = "profile"
        ),
        NavigationItem(
            title = "My bookmarks",
            icon = Icons.Default.Bookmark,
            description = "Bookmark Screen",
            itemId = "bookmarkScreen"
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            description = "Settings Screen",
            itemId = "settingsScreen"
        ),
        NavigationItem(
            title = "Logout",
            icon = Icons.Default.Logout,
            description = "Logout",
            itemId = "logout"
        ),
        NavigationItem(
            title = "Sentiment Analysis",
            icon = Icons.Default.Logout,
            description = "Sentiment Analysis",
            itemId = "Sentiment Analysis"
        )

    )

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val state = mutableStateOf(Users())


    init{
        getDataFromFirebase(){}
    }

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null){
                Log.d("TAG","Inside sign outsuccess")

            }else{
                Log.d("TAG","Inside sign out is not complete")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun checkForActiveSession(){
        if(FirebaseAuth.getInstance().currentUser != null){
           Log.d("TAG","Valid session")
            isUserLoggedIn.value = true
        }else{
            Log.d("TAG","USer is not logged in")
            isUserLoggedIn.value = false
        }
    }


    val emailId: MutableLiveData<String> = MutableLiveData()
    val uId: MutableLiveData<String> = MutableLiveData()

    fun getUserData(){
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also {email->
                emailId.value = email

            }
        }
        FirebaseAuth.getInstance().currentUser?.also {
            it.uid?.also{uid->
                uId.value = uid


        }
        }
    }

    fun getDataFromFirebase(callback: (Users) -> Unit){
        viewModelScope.launch{
            getCloudDataFromFireStore(){
                state.value = it
                Log.v("asdf viewmodelbookmark", state.value.bookmark.toString())
            }


        }
        callback(state.value)
    }
}

@SuppressLint("SuspiciousIndentation")
fun getCloudDataFromFireStore(callback:(Users)->Unit) {
    val db = FirebaseFirestore.getInstance()
    var user = Users()
    val uId: MutableLiveData<String> = MutableLiveData()

    FirebaseAuth.getInstance().currentUser?.also {
        it.uid?.also{uid->
            uId.value = uid
            Log.v("uid",uid)

        }
    }

    val docRef = uId.value?.let { db.collection("Users").document(it) }
      try{
          if (docRef != null) {
              docRef.get().addOnSuccessListener{ documentSnapshot->
                  val result = documentSnapshot.toObject(Users::class.java)
                  user = result!!
                  callback(user)
                  Log.v("User",user.firstName.toString())
              }
          }

      }catch(e: Exception){

      }





}