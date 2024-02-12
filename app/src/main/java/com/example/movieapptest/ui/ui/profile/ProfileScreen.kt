package com.example.movieapptest.ui.ui.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.movieapptest.R
import com.example.movieapptest.Util.StorageUtil
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel

@Composable

fun ProfileScreen(
    navController: NavHostController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    Column() {
        //ProfileImage(mainScreenViewModel)
        ProfileImage(mainScreenViewModel)
        Box(modifier = Modifier.padding(30.dp)) {
            Column() {
                Text(text = "First Name: " + mainScreenViewModel.state.value.firstName)
                Text(text = "Last Name: " + mainScreenViewModel.state.value.lastName)
                Text(text = "Email : " + mainScreenViewModel.state.value.email)
            }
        }


    }

}

@Composable
fun ProfileImage(mainScreenViewModel: MainScreenViewModel) {
    var storageUri:String by remember { mutableStateOf("") }
    storageUri = mainScreenViewModel.state.value.photoUrl
    var imageUri:Uri? by remember { mutableStateOf(null)}
    var loading: Boolean by remember { mutableStateOf(false) }

    StorageUtil.getPhotoFromStorage(storageUri){Uri->
        Log.v("refreshStorage","re")
        imageUri = Uri

    }

    var painter = rememberImagePainter(
        if(storageUri=="") {
            Log.v("painter", storageUri)
            Log.v("painter2",mainScreenViewModel.state.value.photoUrl)
            R.drawable.profile
        }
        else{
            Log.v("painter3", storageUri)
            Log.v("painter4",mainScreenViewModel.state.value.photoUrl)
            Log.v("painter5",imageUri.toString())
            imageUri
            Log.v("imageUri",imageUri.toString())

        }

    )


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()

    ) { uri: Uri? ->
        uri?.let {
            loading = true
            //imageUri.value = it.toString()
            StorageUtil.uploadToStorage(uri, mainScreenViewModel) { it ->
                if (it != null) {
                    storageUri=it
                }
                loading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
                    .fillMaxSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
            if (loading) { // Show loading indicator when loading is true
                CircularProgressIndicator(
                    modifier = Modifier.wrapContentSize()
                )
            }

        }
        Text(text = "Change profile picture")
    }
}


