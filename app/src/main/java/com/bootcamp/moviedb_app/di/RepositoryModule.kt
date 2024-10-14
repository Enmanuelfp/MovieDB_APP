package com.bootcamp.moviedb_app.di

import com.bootcamp.moviedb_app.repository.MoviesRepository
import com.bootcamp.moviedb_app.repository.MoviesRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun MoviesRepository(repositoryImp: MoviesRepositoryImp): MoviesRepository
}