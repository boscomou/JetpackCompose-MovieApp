package com.example

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application() {

        fun OnCreate(){
            super.onCreate()

            FirebaseApp.initializeApp(this)
        }
}