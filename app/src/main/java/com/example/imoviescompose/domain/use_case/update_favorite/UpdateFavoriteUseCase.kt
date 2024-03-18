package com.example.imoviescompose.domain.use_case.update_favorite

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(movie: Movie): Flow<Resource<Boolean>> {
        return if (movie.isFavorite) {
            repository.removeFavorite(movie.id)
        } else {
            repository.addFavorite(movie)
        }
    }
}