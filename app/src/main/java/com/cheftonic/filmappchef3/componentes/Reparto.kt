package com.cheftonic.filmappchef3.componentes

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cheftonic.filmappchef3.model.credits.ResultCreditsCine
import com.cheftonic.filmappchef3.model.tv.CreadorTv

@Composable
fun Reparto(
    resultado: List<ResultCreditsCine>,
    navController: NavHostController,
    context: Context,
)
{
    LazyRow {
        items(resultado){resultado ->
            RepartoItem(resultado, navController, context)
        }
    }
}
@Composable
fun RepartoItem(
    resultado: ResultCreditsCine,
    navController: NavHostController,
    context: Context
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        if (resultado.profile_path != null) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(18.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data("https://image.tmdb.org/t/p/w500${resultado.profile_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                modifier = Modifier.width(100.dp),
                text = "${resultado.original_name}",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.height(
                    25.dp
                )
            )
        }
    }
}

@Composable
fun Directores(
    resultado: List<ResultCreditsCine>,
    navController: NavHostController,
    context: Context,
)
{
    LazyRow {
        items(resultado){resultado ->
            DirectorItem(resultado, navController, context)
        }
    }
}
@Composable
fun DirectorItem(
    resultado: ResultCreditsCine,
    navController: NavHostController,
    context: Context
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        if (resultado.profile_path != null) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(18.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data("https://image.tmdb.org/t/p/w500${resultado.profile_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                modifier = Modifier.width(100.dp),
                text = "${resultado.original_name}",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.height(
                    25.dp
                )
            )
        }
    }
}

@Composable
fun CreadoresTv(
    resultado: List<CreadorTv>,
    navController: NavHostController,
    context: Context,
)
{
    LazyRow {
        items(resultado){resultado ->
            CreadorItem(resultado, navController, context)
        }
    }
}
@Composable
fun CreadorItem(
    resultado: CreadorTv,
    navController: NavHostController,
    context: Context
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        if (resultado.profile_path != null) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(18.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data("https://image.tmdb.org/t/p/w500${resultado.profile_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                modifier = Modifier.width(100.dp),
                text = "${resultado.name}",
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier.height(
                    25.dp
                )
            )
        }
    }
}
