package com.example.imoviescompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imoviescompose.data.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("SELECT *, 1 AS isFavorite FROM favorites")
    suspend fun getFavorites(): List<FavoriteEntity>

    @Query("SELECT * FROM FAVORITES WHERE trackId = :id")
    suspend fun getFavorite(id: Int): FavoriteEntity?

    @Query("DELETE FROM FAVORITES WHERE trackId = :id")
    suspend fun deleteFavorite(id: Int)
}