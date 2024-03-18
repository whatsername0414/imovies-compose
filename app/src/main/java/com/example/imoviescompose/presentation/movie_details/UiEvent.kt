package com.example.imoviescompose.presentation.movie_details

import com.example.imoviescompose.domain.model.movie.Movie

sealed class UiEvent {
    data class UpdateFavorite(val movie: Movie) : UiEvent()
}
