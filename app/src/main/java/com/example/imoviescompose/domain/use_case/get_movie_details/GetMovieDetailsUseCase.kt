package com.example.imoviescompose.domain.use_case.get_movie_details

import com.example.imoviescompose.common.Resource
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<Movie>> {
        return repository.getMovie(id)
    }
}