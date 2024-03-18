package com.example.imoviescompose.presentation.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.imoviescompose.R
import com.example.imoviescompose.common.Extensions.convertToCurrency
import com.example.imoviescompose.common.components.Toolbar
import com.example.imoviescompose.ui.theme.Primary

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val movie = state.movie

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movie?.artwork)
            .size(Size.ORIGINAL)
            .build()
    ).state

    var shouldUpdate by remember { mutableStateOf(true) }

    Column {
        Toolbar(
            title = "Movie Details",
            actionIcon =
            if (movie?.isFavorite == true ) ImageVector.vectorResource(id = R.drawable.ic_favorite_fill)
            else ImageVector.vectorResource(id = R.drawable.ic_favorite),
            onBackButtonClicked = {
                navController
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldUpdate", shouldUpdate)
                navController.navigateUp()
            },
            onActionClicked = {
                shouldUpdate = true
                movie?.let { viewModel.onEvent(UiEvent.UpdateFavorite(movie)) }
            }
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Primary
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(24.dp))

                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .weight(1f)
                ) {
                    if (imageState is AsyncImagePainter.State.Success) {
                        Image(
                            modifier = Modifier
                                .width(60.dp)
                                .height(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            painter = imageState.painter,
                            contentDescription = movie?.title,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = movie?.title.orEmpty(),
                            fontSize = 16.sp,
                            color = Primary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "${movie?.releaseYear} | ${movie?.genre}",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = movie?.currency?.convertToCurrency(movie.price).orEmpty(),
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = movie?.description.orEmpty(),
                fontSize = 12.sp,
                color = Color.Gray,
            )
        }
    }
}