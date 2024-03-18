package com.example.imoviescompose.presentation.movie_details

import com.example.imoviescompose.domain.model.movie.Movie

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = "",

    val shouldRefresh: Boolean = false
)
