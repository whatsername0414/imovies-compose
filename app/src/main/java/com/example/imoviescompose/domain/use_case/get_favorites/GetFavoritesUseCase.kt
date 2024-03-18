package com.example.imoviescompose.domain.use_case.get_favorites

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Movie>>> {
        return repository.getFavorites()
    }
}