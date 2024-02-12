package com.example.statetest2.data

import com.example.movieapptest.data.api.MovieApi
import com.example.movieapptest.data.api.model.Movie
import com.example.movieapptest.data.api.model.Result
import javax.inject.Inject

class MovieRepo @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getPopularMovies(api_key: String, page: String): Movie {
        return movieApi.getPopularMovies(api_key, page)
    }

    suspend fun searchMovie(api_key: String, query: String, page: String): Movie {
        return movieApi.searchMovie(api_key, query, page)
    }

    suspend fun getActionMovies(api_key: String, page: String): Movie {
        return movieApi.getActionMovies(api_key, page)
    }

    suspend fun getAnimationMovies(api_key: String, page: String): Movie {
        return movieApi.getAnimationMovies(api_key, page)
    }

    suspend fun topRateMovies(api_key: String, with_genres: Any, page: String): Movie {
        return movieApi.getTopRateMovies(api_key, with_genres, page)
    }
    suspend fun getIDMovie(api_key: String, movieID:String):Result{
        return movieApi.getIDMovie(movieID,api_key)    }

}