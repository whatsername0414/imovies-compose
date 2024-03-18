package com.example.imoviescompose.domain.use_case.get_movies

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(term: String, isForceUpdate: Boolean): Flow<Resource<List<Movie>>> {
        return repository.getMovies(term, isForceUpdate)
    }
}