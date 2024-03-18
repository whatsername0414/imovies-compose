package com.example.imoviescompose.presentation.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.imoviescompose.common.components.Toolbar
import com.example.imoviescompose.presentation.movies.components.MovieItem
import com.example.imoviescompose.presentation.util.Screen

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val shouldUpdate = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("shouldUpdate") ?: false

    if (shouldUpdate) {
        viewModel.onEvent(UiEvent.UpdateFavorites)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Toolbar(
            title = "Favorites",
            onBackButtonClicked = {
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldUpdate", shouldUpdate)
                navController.navigateUp()
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    count = state.movies.size,
                    key = { state.movies[it].id }
                ) { index ->
                    MovieItem(
                        movie = state.movies[index],
                        onClicked = { id ->
                            navController.navigate(Screen.MovieDetailsScreen.route + "?movieId=$id")
                        },
                        onFavorite = { movie ->
                            viewModel.onEvent(UiEvent.UpdateFavorite(movie))
                        }
                    )
                }
            }
        }
    }
}