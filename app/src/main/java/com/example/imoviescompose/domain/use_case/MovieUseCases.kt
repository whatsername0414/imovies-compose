package com.example.imoviescompose.domain.use_case

import com.example.imoviescompose.domain.use_case.get_favorite.GetFavoriteUseCase
import com.example.imoviescompose.domain.use_case.get_movie_details.GetMovieDetailsUseCase
import com.example.imoviescompose.domain.use_case.get_movies.GetMoviesUseCase
import com.example.imoviescompose.domain.use_case.get_user.GetUserUseCase
import com.example.imoviescompose.domain.use_case.update_favorite.UpdateFavoriteUseCase
import com.example.imoviescompose.domain.use_case.update_user.UpdateUserUseCase

data class MovieUseCases(
    val getMoviesUseCase: GetMoviesUseCase,
    val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    val updateFavoriteUseCase: UpdateFavoriteUseCase,
    val getFavoritesUseCase: GetFavoriteUseCase,
    val getFavoriteUseCase: GetFavoriteUseCase,
    val getUserUseCase: GetUserUseCase,
    val updateUserUseCase: UpdateUserUseCase
)