package com.example.imoviescompose.presentation.favorites

import com.example.imoviescompose.domain.model.movie.Movie

sealed class UiEvent {
    object UpdateFavorites: UiEvent()
    data class UpdateFavorite(val movie: Movie) : UiEvent()
}
