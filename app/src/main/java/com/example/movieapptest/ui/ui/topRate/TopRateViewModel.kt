package com.example.movieapptest.ui.ui.topRate


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statetest2.data.MovieRepo
import com.example.movieapptest.data.api.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRateViewModel @Inject constructor(
    private val movieRepo: MovieRepo
) : ViewModel() {
    private val _state = MutableStateFlow<Movie?>(null)
    val state: StateFlow<Movie?>
        get() = _state

    fun getTopRateMovies(apiKey: String, with_genres: Any, page: String) {
        viewModelScope.launch {
            _state.value = null
            val movies = movieRepo.topRateMovies(apiKey, with_genres, page)
            _state.value = movies
        }

    }
}