package com.example.imoviescompose.domain.mapper

import com.example.imoviescompose.data.dto.MovieDto
import com.example.imoviescompose.data.entity.FavoriteEntity
import com.example.imoviescompose.data.entity.MovieEntity
import com.example.imoviescompose.data.entity.MovieWithFavorite
import com.example.imoviescompose.domain.model.movie.Movie


object MovieMapper {
    fun MovieDto.toMovie(isFavorite: Boolean = false): Movie {
        return Movie(
            id = trackId,
            title = trackName.orEmpty(),
            artwork = artworkUrl100.orEmpty(),
            description = longDescription.orEmpty(),
            price = trackPrice ?: 0.0,
            currency = currency.orEmpty(),
            genre = primaryGenreName.orEmpty(),
            releaseYear = releaseDate?.split("-")?.firstOrNull().orEmpty(),
            isFavorite = isFavorite
        )
    }

    fun MovieWithFavorite.toMovie(): Movie {
        return Movie(
            id = movieEntity.trackId,
            title = movieEntity.trackName,
            artwork = movieEntity.artwork,
            description = movieEntity.longDescription,
            price = movieEntity.price,
            currency = movieEntity.currency,
            genre = movieEntity.genre,
            releaseYear = movieEntity.releaseYear.split("-").firstOrNull().orEmpty(),
            isFavorite = isFavorite()
        )
    }

    fun FavoriteEntity.toMovie(): Movie {
        return Movie(
            id = trackId,
            title = trackName,
            artwork = artwork,
            description = longDescription,
            price = price,
            currency = currency,
            genre = genre,
            releaseYear = releaseYear.split("-").firstOrNull().orEmpty(),
            isFavorite = true
        )
    }

    fun MovieDto.toMovieEntity(): MovieEntity {
        return MovieEntity(
            trackId = trackId,
            trackName = trackName.orEmpty(),
            artwork = artworkUrl100.orEmpty(),
            longDescription = longDescription.orEmpty(),
            price = trackPrice ?: 0.0,
            currency = currency.orEmpty(),
            genre = primaryGenreName.orEmpty(),
            releaseYear = releaseDate.orEmpty(),
        )
    }

    fun Movie.toFavoriteEntity(): FavoriteEntity {
        return FavoriteEntity(
            trackId = id,
            trackName = title,
            longDescription = description,
            artwork = artwork,
            price = price,
            currency = currency,
            genre = genre,
            releaseYear = releaseYear,
            isFavorite = true
        )
    }


}