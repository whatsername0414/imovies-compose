package com.example.imoviescompose.presentation.movies

import com.example.imoviescompose.domain.model.movie.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",

    val term: String = "",
    val isSearching: Boolean = false
)
