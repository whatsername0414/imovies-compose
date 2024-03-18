package com.example.imoviescompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imoviescompose.domain.model.user.User
import com.example.imoviescompose.presentation.favorites.FavoritesScreen
import com.example.imoviescompose.presentation.movie_details.MovieDetailsScreen
import com.example.imoviescompose.presentation.movies.MoviesScreen
import com.example.imoviescompose.presentation.user.UserViewModel
import com.example.imoviescompose.presentation.util.Screen
import com.example.imoviescompose.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme(
                darkTheme = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val userViewModel: UserViewModel = hiltViewModel()
                    val userState = userViewModel.state.collectAsState().value
                    NavHost(
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        navController = navController,
                        startDestination = Screen.MoviesScreen.route
                        ) {
                        composable(
                            route = Screen.MoviesScreen.route,
                        ) {
                            MoviesScreen(
                                navController = navController,
                                userViewModel = userViewModel
                            )
                        }
                        composable(
                            route = Screen.MovieDetailsScreen.route +
                                    "?id={id}",
                            arguments = listOf(
                                navArgument(
                                    name = "id"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            MovieDetailsScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.FavoritesScreen.route) {
                            FavoritesScreen(navController = navController)
                        }
                    }

                    if (userState.user == null) {
                        userViewModel.insertUser(
                            User(
                                lastDateVisit = Date().toString(),
                                lastPageVisit = Screen.MoviesScreen.route
                            )
                        )
                    }
                    userState.user?.let { user ->
                        navController.navigate(
                            user.lastPageVisit + "?id=${user.lastPageVisitParam}")
                    }
                    navController.addOnDestinationChangedListener { controller, destination, _ ->
                        val param = controller.currentBackStackEntry?.arguments?.getInt("id")
                        if (userState.user == null) {
                            userViewModel.updateUser(
                                User(
                                    lastDateVisit = Date().toString(),
                                    lastPageVisit = destination.route?.split("?")?.first().orEmpty(),
                                    lastPageVisitParam = param
                                )
                            )
                        } else {
                            userViewModel.updateUser(
                                userState.user.copy(
                                    lastDateVisit = Date().toString(),
                                    lastPageVisit = destination.route?.split("?")?.first().orEmpty(),
                                    lastPageVisitParam = param
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}