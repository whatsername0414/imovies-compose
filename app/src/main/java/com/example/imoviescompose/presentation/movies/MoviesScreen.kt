package com.example.imoviescompose.presentation.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.imoviescompose.R
import com.example.imoviescompose.common.Constants
import com.example.imoviescompose.common.Extensions.formatDate
import com.example.imoviescompose.common.components.Toolbar
import com.example.imoviescompose.presentation.movies.components.MovieItem
import com.example.imoviescompose.presentation.user.UserViewModel
import com.example.imoviescompose.presentation.util.Screen
import com.example.imoviescompose.ui.theme.Primary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(
    navController: NavController,
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val moviesState = moviesViewModel.state.collectAsState().value
    val userState = userViewModel.state.collectAsState().value

    val pullRefreshState = rememberPullRefreshState(moviesState.isLoading, {
        moviesViewModel.onEvent(UiEvent.Search(moviesState.term))
    })

    val shouldUpdate = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.get<Boolean>("shouldUpdate") ?: false
    if (shouldUpdate) {
        moviesViewModel.onEvent(UiEvent.UpdateMovies)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Toolbar(
            searchPlaceholder = "Search Movies",
            backButtonIcon = null,
            actionIcon = ImageVector.vectorResource(id = R.drawable.ic_favorite),
            onActionClicked = {
                navController.navigate(Screen.FavoritesScreen.route)
            },
            onSearchSubmitted = { term ->
                moviesViewModel.onEvent(UiEvent.Search(term))
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row {
                Text(
                    text = "Last Visit:",
                    fontSize = 12.sp,
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = userState.user?.lastDateVisit?.formatDate(
                        Constants.EEE_MMM_DD_HH_MM_SS_ZZZZ_YYY,
                        Constants.MMM_DD_YYY_HH_MM_AA
                    ) ?: "Now",
                    fontSize = 12.sp,
                    color = Primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier
                .pullRefresh(pullRefreshState)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = moviesState.movies.size,
                        key = { moviesState.movies[it].id }
                    ) { index ->
                        MovieItem(
                            movie = moviesState.movies[index],
                            onClicked = { id ->
                                navController.navigate(Screen.MovieDetailsScreen.route + "?id=$id")
                            },
                            onFavorite = { movie ->
                                moviesViewModel.onEvent(UiEvent.UpdateFavorite(movie))
                            }
                        )
                    }
                }

                PullRefreshIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    refreshing = moviesState.isLoading,
                    state = pullRefreshState,
                    contentColor = Primary
                )
            }
        }
    }
}