package com.example.imoviescompose.presentation.movies

import com.example.imoviescompose.domain.model.movie.Movie

sealed class UiEvent {
    object UpdateMovies: UiEvent()
    data class UpdateFavorite(val movie: Movie) : UiEvent()
    data class Search(val term: String): UiEvent()
}
