package com.bootcamp.moviedb_app.state

import com.bootcamp.moviedb_app.model.Movie

data class MoviesState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

