package com.bootcamp.moviedb_app.model

data class MoviesResponse(
    val page:Int,
    val results:List<Movie>
)

data class Movie(
    val id :Long,
    val original_title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float
)
