package com.example.imoviescompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imoviescompose.data.entity.MovieEntity
import com.example.imoviescompose.data.entity.MovieWithFavorite

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT movies.*, CASE WHEN favorites.trackId IS NULL THEN 0 ELSE 1 END AS isFavorite " +
            "FROM movies " +
            "LEFT JOIN favorites ON movies.trackId = favorites.trackId " +
            "WHERE movies.trackName LIKE '%' || :term || '%'")
    suspend fun getMovies(term: String): List<MovieWithFavorite>
}