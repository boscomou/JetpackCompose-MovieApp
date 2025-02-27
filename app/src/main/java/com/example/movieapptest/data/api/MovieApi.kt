package com.example.movieapptest.data.api

import com.example.movieapptest.data.api.model.Movie
import com.example.movieapptest.data.api.model.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/discover/movie?include_adult=false&include_video=false&language=en-US&sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: String

    ): Movie

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") key: String,
        @Query("query") query: String,
        @Query("page") page: String
    ): Movie

    @GET("/3/discover/movie?with_genres=28")
    suspend fun getActionMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: String
    ): Movie

    @GET("/3/discover/movie?with_genres=16")
    suspend fun getAnimationMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: String
    ): Movie

    @GET("/3/discover/movie?language=en-US&sort_by=vote_average.desc")
    suspend fun getTopRateMovies(
        @Query("api_key") api_key: String,
        @Query("with_genres") with_genres: Any,
        @Query("page") page: String
    ): Movie

    @GET("/3/movie/{movie_id}?")
    suspend fun getIDMovie(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,

        ): Result



}
