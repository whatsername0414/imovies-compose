package com.example.imoviescompose.domain.repository

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun addFavorite(movie: Movie): Flow<Resource<Boolean>>
    suspend fun removeFavorite(id: Int): Flow<Resource<Boolean>>
    suspend fun getFavorite(id: Int): Flow<Resource<Movie>>
    suspend fun getFavorites(): Flow<Resource<List<Movie>>>
}