package com.example.movieapptest.ui.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapptest.data.api.ApiConstants
import com.example.movieapptest.data.api.model.Movie
import com.example.statetest2.data.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {
    private val _state = MutableStateFlow<Movie?>(null)
    val state: StateFlow<Movie?>
        get() = _state

    init {
        viewModelScope.launch {
            val movies = movieRepo.getPopularMovies(ApiConstants.API_KEY, "1")
            _state.value = movies
        }
    }

    fun getPopularMovies(apiKey: String, page: String) {
        _state.value = null
        viewModelScope.launch {
            val movies = movieRepo.getPopularMovies(apiKey, page)
            _state.value = movies
        }

    }
}
 