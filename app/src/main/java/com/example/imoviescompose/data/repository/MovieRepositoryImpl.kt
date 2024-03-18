package com.example.imoviescompose.data.repository

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.data.api.MovieApi
import com.example.imoviescompose.data.local.FavoriteDao
import com.example.imoviescompose.data.local.MovieDao
import com.example.imoviescompose.domain.mapper.MovieMapper.toMovie
import com.example.imoviescompose.domain.mapper.MovieMapper.toMovieEntity
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieApi,
    private val movieDao: MovieDao,
    private val favoriteDao: FavoriteDao,
) : MovieRepository {

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading)
            val data = try {
                val res = movieService.getMovie(id = id)
                if (!res.isSuccessful) {
                    emit(Resource.Error(res.message() ?: "An unexpected error occurred"))
                    return@flow
                } else {
                    res.body()?.results.orEmpty()
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                return@flow
            }
            val isFavorite = favoriteDao.getFavorite(id = id) != null
            val movie = data.first().toMovie(isFavorite)
            emit(Resource.Success(movie))
        }
    }

    override suspend fun getMovies(
        term: String,
        isForceUpdate: Boolean
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading)
            if (!isForceUpdate) {
                val result = movieDao.getMovies(term = term)
                val movies = result.map { movie -> movie.toMovie() }
                emit(Resource.Success(movies))
                return@flow
            }
            val data = try {
                val res = movieService.getMovies(term = term)
                if (!res.isSuccessful) {
                    emit(Resource.Error(res.message() ?: "An unexpected error occurred"))
                    return@flow
                } else {
                    res.body()?.results.orEmpty()
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
                return@flow
            }
            val result = data.map { movie -> movie.toMovieEntity() }
            movieDao.insertMovies(result)
            val movies = movieDao.getMovies(term).map { movie -> movie.toMovie() }
            emit(Resource.Success(movies))
        }
    }

}