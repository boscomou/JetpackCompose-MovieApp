package com.example.movieapptest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.RegistrationAndLogin.screens.LoginScreen
import com.example.RegistrationAndLogin.screens.SignupScreen
import com.example.movieapptest.Bookmark.BookmarkScreen
import com.example.movieapptest.ui.theme.MovieAppTestTheme
import com.example.movieapptest.ui.ui.MainScreen.MainScreen
import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel
import com.example.movieapptest.ui.ui.SentimentAnalysisScreen.SentimentAnalysisScreen
import com.example.movieapptest.ui.ui.genres.ActionScreen
import com.example.movieapptest.ui.ui.genres.AnimationScreen
import com.example.movieapptest.ui.ui.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainScreenViewModel: MainScreenViewModel by viewModels()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTestTheme {
                val navController = rememberNavController()
                var startScreen = ""
                mainScreenViewModel.checkForActiveSession()
                if(mainScreenViewModel.isUserLoggedIn.value==true){
                    startScreen = "MainScreen"
                }
                else{
                    startScreen = "LoginScreen"
                }
                NavHost(
                    navController = navController,
                    startDestination = startScreen
                ) {
                    composable("SignupScreen"){
                        SignupScreen(navController = navController)
                    }
                    composable("LoginScreen"){
                        LoginScreen(navController = navController)
                    }
                    composable("MainScreen") {
                        MainScreen(navController = navController)
                    }
                    composable("ActionScreen") {
                        ActionScreen(navController = navController)
                    }
                    composable("AnimationScreen") {
                        AnimationScreen(navController = navController)
                    }
                    composable("ProfileScreen"){
                        ProfileScreen(navController = navController)
                    }
                    composable("BookmarkScreen"){
                        BookmarkScreen(navController = navController)
                    }
                    composable("SentimentAnalysisScreen"){
                        SentimentAnalysisScreen(navController =navController)
                    }


                }


            }
        }

    }
}

