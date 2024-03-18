package com.example.imoviescompose.domain.repository

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>

    suspend fun getMovies(term: String, isForceUpdate: Boolean): Flow<Resource<List<Movie>>>
}