package com.bootcamp.moviedb_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesEntity(
    @PrimaryKey
    val id: Long,
    val original_title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Float
)
