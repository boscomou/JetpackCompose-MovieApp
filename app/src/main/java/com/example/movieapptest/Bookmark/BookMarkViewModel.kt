package com.example.movieapptest.Bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapptest.data.api.model.Result
import com.example.statetest2.data.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {
    private val _state = MutableStateFlow<Result?>(null)
    val state: StateFlow<Result?>
        get() = _state

    fun getIDMovies(apiKey: String, movieID: String, callback:(Result)->Unit) {
        _state.value = null
        viewModelScope.launch {
            val results = movieRepo.getIDMovie(apiKey, movieID)
            _state.value = results
            callback(results)
        }


    }
}