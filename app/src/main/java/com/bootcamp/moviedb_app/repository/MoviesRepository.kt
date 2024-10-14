package com.bootcamp.moviedb_app.repository

import android.util.Log
import com.bootcamp.moviedb_app.datasource.RestDataSource
import com.bootcamp.moviedb_app.model.Movie
import com.bootcamp.moviedb_app.model.MoviesDao
import com.bootcamp.moviedb_app.model.MoviesEntity

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getMoviesFromApi(page: Int): List<Movie>
   suspend fun getMoviesFromDb(): Flow<List<MoviesEntity>>
}

class MoviesRepositoryImp @Inject constructor(
    private val restDataSource: RestDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override suspend fun getMoviesFromApi(page: Int): List<Movie> {
        val response = restDataSource.getMovies(page = page)
        Log.d("MoviesRepository", "Fetching movies from API, page: $page")
        if (response.isSuccessful) {

            Log.d("MoviesRepository", "Response successful: ${response.body()}")
            val moviesResponse = response.body()?.results ?: emptyList()

            moviesResponse.forEach { movie ->
                val movieEntity = MoviesEntity(
                    id = movie.id,
                    original_title = movie.original_title,
                    poster_path = movie.poster_path,
                    release_date = movie.release_date,
                    vote_average = movie.vote_average

                )
                moviesDao.insertMovies(listOf(movieEntity))
            }

            return moviesResponse
        } else {
            Log.e("MoviesRepository", "Error fetching movies: ${response.code()} ${response.message()}") // Log de error
            throw Exception("Error al recuperar la informacion desde la api")
        }

    }

    override  suspend fun getMoviesFromDb(): Flow<List<MoviesEntity>> {
        return moviesDao.getMovies()
    }

}