package com.bootcamp.moviedb_app.datasource

import com.bootcamp.moviedb_app.model.Movie
import com.bootcamp.moviedb_app.model.MoviesResponse
import com.bootcamp.moviedb_app.util.Constants.Companion.API_KEY
import com.bootcamp.moviedb_app.util.Constants.Companion.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestDataSource {

    @GET(ENDPOINT)
    suspend fun getMovies(
        @Query("api_key") apikey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse
}