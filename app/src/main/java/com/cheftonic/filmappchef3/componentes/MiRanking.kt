package com.cheftonic.filmappchef3.componentes

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cheftonic.filmappchef3.R
import com.cheftonic.filmappchef3.model.cine.ResultCine
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.navigation.AppScreens


@Composable
fun MiRankingCine(
    misPeliculas: List<ResultCine>,
    navController: NavHostController,
    context: Context
) {
    Box(
        modifier = Modifier
            .height(415.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            )
     {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            itemsIndexed(misPeliculas) { index, pelicula ->
                MiRankingItem(index + 1, pelicula, navController, context)
            }
        }
    }
}


@Composable
fun MiRankingItem(
    index: Int,
    pelicula: ResultCine,
    navController: NavHostController,
    context: Context
) {
    if (pelicula.peli_voto!! > 1) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)
    ) {

            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(75.dp)
                    .padding(2.dp),
                Alignment.Center
            ) {
                if (index == 1) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(R.drawable.ic_oro),
                        modifier = Modifier.size(30.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                } else if (index == 2) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(R.drawable.ic_plata),
                        modifier = Modifier.size(28.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                } else if (index == 3) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(R.drawable.ic_bronce),
                        modifier = Modifier.size(25.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                } else {
                    Text(
                        text = index.toString() + ".",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 2.5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(75.dp),
                Alignment.CenterStart
            ) {
                Text(
                    text = pelicula.title,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 2.5.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Box(
                modifier = Modifier
                    .width(25.dp)
                    .height(75.dp)
                    .padding(0.8.dp),
                Alignment.CenterEnd
            ) {
                Text(
                    text = "${pelicula.peli_voto}",
                    fontSize = 18.sp,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(Modifier.width(2.dp))

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "",
                tint = Color.Yellow
            )

            Box(
                modifier = Modifier
                    .width(65.dp)
                    .height(65.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        navController.navigate(
                            AppScreens.TituloCineScreen.passData(
                                Uri.encode(pelicula.id.toString()),
                                Uri.encode(pelicula.poster_path),
                                Uri.encode(pelicula.title),
                                Uri.encode(pelicula.overview),
                                Uri.encode(pelicula.release_date)
                            )
                        )
                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500${pelicula.poster_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.CenterEnd
                )
            }
        }
        Spacer(Modifier.height(2.5.dp))
    }
}

@Composable
fun MiRankingTv(
    misSeries: List<ResultTv>,
    navController: NavHostController,
    context: Context
) {
    Box(
        modifier = Modifier
            .height(420.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        LazyColumn {
            itemsIndexed(misSeries) { index, serie ->
                MiRankingTvItem(index +1, serie, navController, context)
            }
        }
    }
}


@Composable
fun MiRankingTvItem(
    index: Int,
    serie: ResultTv,
    navController: NavHostController,
    context: Context
) {

    if (serie.serie_voto!! > 1) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary)
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(75.dp)
                    .padding(2.dp),
                Alignment.Center
            ) {
                if (index == 1) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(R.drawable.ic_oro),
                        modifier = Modifier.size(30.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                } else if (index == 2) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(R.drawable.ic_plata),
                        modifier = Modifier.size(28.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                } else if (index == 3) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(R.drawable.ic_bronce),
                        modifier = Modifier.size(25.dp)
                            .align(Alignment.Center),
                        tint = Color.Unspecified
                    )
                } else {
                    Text(
                        text = index.toString() + ".",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 2.5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(75.dp)
                    .padding(2.dp),
                Alignment.CenterStart
            ) {
                Text(
                    text = serie.name,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 2.5.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Box(
                modifier = Modifier
                    .width(25.dp)
                    .height(75.dp)
                    .padding(2.dp),
                Alignment.CenterStart
            ) {
                Text(
                    text = "${serie.serie_voto}",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "",
                tint = Color.Yellow
            )

            Box(
                modifier = Modifier
                    .width(65.dp)
                    .height(65.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .clickable {
                        navController.navigate(
                            AppScreens.TituloCineScreen.passData(
                                Uri.encode(serie.id.toString()),
                                Uri.encode(serie.poster_path),
                                Uri.encode(serie.name),
                                Uri.encode(serie.overview),
                                Uri.encode(serie.first_air_date)
                            )
                        )
                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/w500${serie.poster_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.CenterEnd
                )
            }
        }
        Spacer(Modifier.height(5.dp))
    }
}

