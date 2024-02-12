package com.example.movieapptest.Bookmark

import com.example.movieapptest.ui.ui.MainScreen.MainScreenViewModel

fun checkWhetherItIsBookmarked(movieId:String,mainScreenViewModel: MainScreenViewModel,callback:(Boolean) -> Unit){

    if(mainScreenViewModel.state.value.bookmark.contains(movieId)){
        callback(true)
    }
    else{
        callback(false)
    }

}