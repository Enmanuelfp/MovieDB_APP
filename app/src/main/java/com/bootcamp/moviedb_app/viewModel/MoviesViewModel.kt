package com.bootcamp.moviedb_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.moviedb_app.model.Movie
import com.bootcamp.moviedb_app.repository.MoviesRepository
import com.bootcamp.moviedb_app.state.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
): ViewModel(){

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state
    init {
        loadMovies(1) // Carga inicial de películas
    }
    fun loadMovies(page:Int){
        viewModelScope.launch {
            _state.value = MoviesState(isLoading = true)
            Log.d("MoviesViewModel", "Loading movies for page: $page")
            try {
                val movies = repo.getMoviesFromApi(page)
                _state.value = MoviesState(movies = movies)
            }catch (e:Exception){
                _state.value = MoviesState(errorMessage = e.message)
            }
        }
    }
    fun loadMoviesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMoviesFromDb().collect { moviesEntityList ->
                // Convertir MoviesEntity a Movie
                val movies = moviesEntityList.map { movieEntity ->
                    Movie(
                        id = movieEntity.id,
                        original_title = movieEntity.original_title,
                        poster_path = movieEntity.poster_path,
                        release_date = movieEntity.release_date,
                        vote_average = movieEntity.vote_average
                    )
                }
                // Cambiar a Dispatchers.Main para actualizar el estado
                withContext(Dispatchers.Main) {
                    _state.value = MoviesState(movies = movies)
                }
            }
        }
    }





}