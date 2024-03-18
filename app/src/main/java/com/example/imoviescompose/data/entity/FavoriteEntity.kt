package com.example.imoviescompose.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class FavoriteEntity(
    @PrimaryKey
    val trackId: Int,
    val trackName: String,
    val longDescription: String,
    val artwork: String,
    val price: Double,
    val currency: String,
    val releaseYear: String,
    val genre: String,
    val isFavorite: Boolean
)