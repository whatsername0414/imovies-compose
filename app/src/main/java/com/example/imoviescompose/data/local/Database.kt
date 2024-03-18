package com.example.imoviescompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imoviescompose.data.entity.FavoriteEntity
import com.example.imoviescompose.data.entity.MovieEntity
import com.example.imoviescompose.data.entity.UserEntity

@Database(
    entities = [UserEntity::class, MovieEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao
}