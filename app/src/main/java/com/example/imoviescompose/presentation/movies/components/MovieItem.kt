package com.example.imoviescompose.presentation.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.imoviescompose.R
import com.example.imoviescompose.common.Extensions.convertToCurrency
import com.example.imoviescompose.domain.model.movie.Movie
import com.example.imoviescompose.ui.theme.Blue9FF
import com.example.imoviescompose.ui.theme.Primary
import com.example.imoviescompose.ui.theme.White0F0

@Composable
fun MovieItem(
    movie: Movie,
    onClicked: (Int) -> Unit,
    onFavorite: (Movie) -> Unit,
) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movie.artwork)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Blue9FF,
                        White0F0
                    )
                )
            )
            .clickable {
                onClicked(movie.id)
            }
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
                    contentDescription = movie.title,
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
                    text = movie.title,
                    fontSize = 16.sp,
                    color = Primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${movie.releaseYear} | ${movie.genre}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movie.currency.convertToCurrency(movie.price),
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Icon(
            modifier = Modifier
                .padding(8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onFavorite(movie) },
            imageVector =
            if (movie.isFavorite) ImageVector.vectorResource(id = R.drawable.ic_favorite_fill)
            else ImageVector.vectorResource(id = R.drawable.ic_favorite),
            tint = Primary,
            contentDescription = "favorite"
        )

    }

    Spacer(modifier = Modifier.height(16.dp))
}