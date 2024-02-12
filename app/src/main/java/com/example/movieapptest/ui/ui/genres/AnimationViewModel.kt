package com.example.movieapptest.ui.ui.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statetest2.data.MovieRepo
import com.example.movieapptest.data.api.ApiConstants
import com.example.movieapptest.data.api.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimationViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {
    private val _state = MutableStateFlow<Movie?>(null)
    val state: StateFlow<Movie?>
        get() = _state

    init {
        viewModelScope.launch {
            val movies = movieRepo.getAnimationMovies(ApiConstants.API_KEY, "1")
            _state.value = movies
        }
    }

    fun getActionMovies(apiKey: String, page: String) {
        viewModelScope.launch {
            _state.value = null
            val movies = movieRepo.getAnimationMovies(apiKey, page)
            _state.value = movies
        }

    }
}