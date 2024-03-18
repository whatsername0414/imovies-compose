package com.example.imoviescompose.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.use_case.get_favorites.GetFavoritesUseCase
import com.example.imoviescompose.domain.use_case.get_movies.GetMoviesUseCase
import com.example.imoviescompose.domain.use_case.update_favorite.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(MoviesState())
    val state = _state.asStateFlow()

    init {
        _state.value = state.value.copy(term = "star")
        getMovies(isForceUpdate = true)
    }

    fun onEvent(event: UiEvent) {
        when(event) {
            is UiEvent.UpdateMovies -> updateMovies()
            is UiEvent.UpdateFavorite -> updateFavorite(event.movie)
            is UiEvent.Search -> {
                _state.value = state.value.copy(term = event.term)
                getMovies(isForceUpdate = true)
            }
        }
    }

    private fun getMovies(isForceUpdate: Boolean = false) {
        viewModelScope.launch {
            val term = state.value.term
            getMoviesUseCase(term, isForceUpdate).collectLatest { res ->
                when(res) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = res.error
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            movies = res.data
                        )
                    }
                }
            }
        }
    }

    private fun updateFavorite(movie: Movie) {
        viewModelScope.launch {
            updateFavoriteUseCase(movie).collectLatest { res ->
                when(res) {
                    is Resource.Loading -> Unit
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        val movies = state.value.movies.map {
                            if (it.id == movie.id) {
                                it.copy(isFavorite = res.data)
                            } else it
                        }
                        _state.value = state.value.copy(movies = movies)
                    }
                }
            }
        }
    }

    private fun updateMovies() {
        viewModelScope.launch {
            getFavoritesUseCase().collectLatest { res ->
                when(res) {
                    is Resource.Error -> Unit
                    Resource.Loading -> Unit
                    is Resource.Success -> {
                        val updatedMovies = state.value.movies.map { item ->
                            val movie = res.data.firstOrNull { it.id == item.id}
                            item.copy(isFavorite = movie?.isFavorite ?: false)
                        }
                        _state.value = state.value.copy(movies = updatedMovies)
                    }
                }
            }
        }
    }
}