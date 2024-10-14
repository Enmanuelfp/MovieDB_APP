package com.bootcamp.moviedb_app.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertMovies(movies:List<MoviesEntity>)

    @Query("SELECT * FROM movies")
     fun  getMovies(): Flow<List<MoviesEntity>>
}