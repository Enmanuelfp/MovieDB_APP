package com.bootcamp.moviedb_app.state

import com.bootcamp.moviedb_app.model.Movie
import com.bootcamp.moviedb_app.model.MoviesEntity

data class MoviesState(
    val movies: List<MoviesEntity> = emptyList(),
    var isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedMovie: Movie? = null,
    val displayedMovies: List<Any> = emptyList()
)

