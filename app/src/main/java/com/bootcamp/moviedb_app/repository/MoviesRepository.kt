package com.bootcamp.moviedb_app.repository

import android.util.Log
import com.bootcamp.moviedb_app.datasource.RestDataSource
import com.bootcamp.moviedb_app.model.Movie
import com.bootcamp.moviedb_app.model.MoviesDao
import com.bootcamp.moviedb_app.model.MoviesEntity
import com.bootcamp.moviedb_app.util.Constants.Companion.IMAGE_BASE_URL


import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesRepository {

    suspend fun getOneMovieFromApi(index: Int): MoviesEntity

     fun getMoviesFromDb(): Flow<List<MoviesEntity>>

    suspend fun deleteOneMovie(toDelete: MoviesEntity)
}

class MoviesRepositoryImp @Inject constructor(
    private val restDataSource: RestDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository {
    override suspend fun getOneMovieFromApi(index: Int): MoviesEntity {
        val response = restDataSource.getMovies().results // Obtén la lista completa de películas
        if (response.isNotEmpty() && index in response.indices) {
            val movieResponse = response[index] // Usa el índice proporcionado

            val movie = MoviesEntity(
                id = movieResponse.id,
                original_title = movieResponse.original_title,
                poster_path = IMAGE_BASE_URL + movieResponse.poster_path,
                release_date = movieResponse.release_date,
                vote_average = movieResponse.vote_average
            )
            moviesDao.insertOneMovie(movie)
            return movie
        } else {
            throw Exception("Índice fuera de rango o no hay películas disponibles.")
        }
    }




    override fun getMoviesFromDb(): Flow<List<MoviesEntity>> {
        return moviesDao.getMovies()
    }



    override suspend fun deleteOneMovie(toDelete: MoviesEntity) = moviesDao.deleteMovie(toDelete)
}






