package com.example.imoviescompose.presentation.favorites


import com.example.imoviescompose.domain.model.movie.Movie

data class FavoritesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)
