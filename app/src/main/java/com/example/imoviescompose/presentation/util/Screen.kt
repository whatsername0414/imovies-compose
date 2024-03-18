package com.example.imoviescompose.presentation.util

sealed class Screen(val route: String) {
    object MoviesScreen: Screen("movies_screen")
    object MovieDetailsScreen: Screen("movie_details_screen")
    object FavoritesScreen: Screen("favorites_screen")
}
