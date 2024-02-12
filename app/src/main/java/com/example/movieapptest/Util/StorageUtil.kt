package com.example.movieapptest.Util

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class StorageUtil {
    companion object {

        fun uploadToStorage(
            uri: Uri,
            mainScreenViewModel: MainScreenViewModel,
            callback: (String?) -> Unit
        ) {
            if(mainScreenViewModel.state.value.photoUrl!="") {
                deleteOldPhoto(mainScreenViewModel)
            }
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val photoUri: Uri = uri /* Get the Uri of the selected photo */
            val unique_image_name = UUID.randomUUID()
            val path = "images/$unique_image_name.jpg"
            val photosRef = storageRef.child(path)
            val uploadTask = photosRef.putFile(photoUri)

            uploadTask.addOnSuccessListener { taskSnapshot ->
                /*// Get the download URL of the uploaded photo
                val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl

                // Save the photo URL in Firestore
                val photoData = downloadUrl.toString()*/
                mainScreenViewModel.state.value.photoUrl = path
                val uId: MutableLiveData<String> = MutableLiveData()
                FirebaseAuth.getInstance().currentUser?.also {
                    it.uid?.also { uid ->
                        uId.value = uid
                    }
                }
                // on below line creating an instance of firebase firestore.
                val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                val dbUsers: CollectionReference = db.collection("Users")

                dbUsers.document(uId.value.toString()).set(mainScreenViewModel.state.value)
                    .addOnSuccessListener { documentReference ->
                        // Photo upload and URL save successful
                        Log.v("PHOOTO", photoUri.toString())
                        callback(path)
                    }
                    .addOnFailureListener { e ->
                        // Handle any errors
                    }
            }.addOnFailureListener { e ->
                // Handle any errors
            }


        }

        fun deleteOldPhoto(mainScreenViewModel:MainScreenViewModel){
            val path = mainScreenViewModel.state.value.photoUrl
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val photosRef = storageRef.child(path)
            photosRef.delete().addOnSuccessListener {

            }.addOnFailureListener {

            }

        }

        fun getPhotoFromStorage(
            Uri: String?,
            callback: (Uri?) -> Unit
        ) {
            if (Uri != null) {
                Log.v("view",Uri)
            }
            if (Uri!= null && Uri.isNotEmpty()) {
                var photoByte: Uri? = null
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference
                val photosRef = storageRef.child(Uri)
                photosRef.downloadUrl.addOnSuccessListener { it ->
                    callback(it)
                    Log.v("phototag", photoByte.toString())

                }.addOnFailureListener {
                    callback(null)
                    Log.v("phototag", "fail")


                }
            }
            else{
                callback(null)

            }

        }
    }
}