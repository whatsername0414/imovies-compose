package com.example.imoviescompose.domain.model.movie


data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val artwork: String,
    val price: Double,
    val currency: String,
    val genre: String,
    val releaseYear: String,
    val isFavorite: Boolean
)