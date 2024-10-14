package com.bootcamp.moviedb_app.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bootcamp.moviedb_app.model.MoviesDao
import com.bootcamp.moviedb_app.model.MoviesEntity

@Database(
    entities = [MoviesEntity::class],
    version = 1
)

abstract class DbDataSource :RoomDatabase(){
    abstract fun moviesDao():MoviesDao
}