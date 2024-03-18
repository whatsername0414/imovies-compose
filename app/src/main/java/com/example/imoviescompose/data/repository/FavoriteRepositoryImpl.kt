package com.example.imoviescompose.data.repository

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.data.local.FavoriteDao
import com.example.imoviescompose.domain.mapper.MovieMapper.toFavoriteEntity
import com.example.imoviescompose.domain.mapper.MovieMapper.toMovie
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {
    override suspend fun addFavorite(movie: Movie): Flow<Resource<Boolean>> {
        return flow {
            try {
                favoriteDao.insertFavorite(movie.toFavoriteEntity())
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error("Unable to add favorite"))
            }
        }
    }

    override suspend fun removeFavorite(id: Int): Flow<Resource<Boolean>> {
        return flow {
            try {
                favoriteDao.deleteFavorite(id)
                emit(Resource.Success(false))
            } catch (e: Exception) {
                emit(Resource.Error("Unable to add favorite"))
            }
        }
    }

    override suspend fun getFavorite(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading)
            val movie = favoriteDao.getFavorite(id)?.toMovie()
            if (movie == null) {
                emit(Resource.Error("Unable to retrieve movie"))
                return@flow
            }
            emit(Resource.Success(movie))
        }
    }

    override suspend fun getFavorites(): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading)
            val movies = favoriteDao.getFavorites().map { movie -> movie.toMovie() }
            emit(Resource.Success(movies))
        }
    }
}