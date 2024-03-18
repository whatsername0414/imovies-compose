package com.example.imoviescompose.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.use_case.get_favorites.GetFavoritesUseCase
import com.example.imoviescompose.domain.use_case.update_favorite.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        getFavorites()
    }

    fun onEvent(event: UiEvent) {
        when(event) {
            is UiEvent.UpdateFavorites -> updateFavorites()
            is UiEvent.UpdateFavorite -> updateFavorite(event.movie)
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collectLatest { res ->
                when(res) {
                    is Resource.Loading -> _state.value = FavoritesState(isLoading = true)
                    is Resource.Error -> _state.value = FavoritesState(error = res.error)
                    is Resource.Success -> _state.value = FavoritesState(movies = res.data)
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

    private fun updateFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collectLatest { res ->
                when(res) {
                    is Resource.Error -> Unit
                    Resource.Loading -> Unit
                    is Resource.Success -> {
                        _state.value = state.value.copy(movies = res.data)
                    }
                }
            }
        }
    }
}