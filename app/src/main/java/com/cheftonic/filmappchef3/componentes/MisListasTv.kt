package com.cheftonic.filmappchef3.componentes

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.navigation.AppScreens


@Composable
fun MisListasTv(
    misSeries: List<ResultTv>,
    navController: NavHostController,
    context: Context
) {
    LazyRow {
        items(misSeries) { serie ->
            MiTvItem(serie, navController, context)
        }
    }
}

@Composable
fun MiTvItem(
    serie: ResultTv,
    navController: NavHostController,
    context: Context
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(230.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    navController.navigate(
                        AppScreens.TituloTvScreen.passData(
                            Uri.encode(serie.id.toString()),
                            Uri.encode(serie.poster_path),
                            Uri.encode(serie.name),
                            Uri.encode(serie.overview),
                            Uri.encode(serie.first_air_date)
                        )
                    )
                }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("https://image.tmdb.org/t/p/w500${serie.poster_path}")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = serie.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = serie.first_air_date.substring(0, 4),
                        textAlign = TextAlign.Start,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        Modifier
                            .padding(0.dp),
                        Arrangement.Absolute.SpaceEvenly,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${serie.serie_voto}",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "",
                            tint = Color.Yellow,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}