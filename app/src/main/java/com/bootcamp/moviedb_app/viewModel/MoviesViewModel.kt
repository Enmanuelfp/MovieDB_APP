package com.bootcamp.moviedb_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.moviedb_app.model.MoviesEntity
import com.bootcamp.moviedb_app.repository.MoviesRepository
import com.bootcamp.moviedb_app.state.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state

    private val _isLoading: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow<Boolean>(false)
    }

    private var nextIndex = 0

    val isLoading: Flow<Boolean> get() = _isLoading

    val movies: Flow<List<MoviesEntity>> by lazy {
        repo.getMoviesFromDb()
    }
     fun loadMoviesFromDb() {
        viewModelScope.launch {
            repo.getMoviesFromDb().collect { moviesList ->
                _state.value = _state.value.copy(movies = moviesList)
            }
        }
    }


    fun addMovie() {
        if (!_isLoading.value) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.value = true
                try {
                    repo.getOneMovieFromApi(nextIndex)
                    nextIndex++ // Incrementar el índice para la próxima adición
                } catch (e: Exception) {
                    // Manejar el error si el índice está fuera de rango
                }
                _isLoading.value = false
                loadMoviesFromDb()
            }
        }
    }

    fun deleteMovie(toDelete: MoviesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteOneMovie(toDelete)
            nextIndex--
        }
    }

}