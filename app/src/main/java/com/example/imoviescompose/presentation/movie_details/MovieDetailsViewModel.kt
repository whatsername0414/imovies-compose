package com.example.imoviescompose.presentation.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import com.example.imoviescompose.domain.use_case.update_favorite.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    init {
        getMovie(savedStateHandle.get<Int>("id") ?: -1)
    }

    fun onEvent(event: UiEvent) {
        when(event) {
            is UiEvent.UpdateFavorite -> updateFavorite(event.movie)
        }
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovieDetailsUseCase(id).collectLatest { res ->
                when(res) {
                    is Resource.Loading -> _state.value = MovieDetailsState(isLoading = true)
                    is Resource.Error -> _state.value = MovieDetailsState(error = res.error)
                    is Resource.Success -> _state.value = MovieDetailsState(movie = res.data)
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
                    is Resource.Success -> _state.value = state.value.copy(
                        movie = state.value.movie?.copy(isFavorite = res.data),
                        shouldRefresh = true
                    )
                }
            }
        }
    }
}