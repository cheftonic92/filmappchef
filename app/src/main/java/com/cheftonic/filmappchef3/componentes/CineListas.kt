package com.cheftonic.filmappchef3.componentes

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cheftonic.filmappchef3.model.cine.ResultCine
import com.cheftonic.filmappchef3.navigation.AppScreens

@Composable
fun CineLista(
    resultado: List<ResultCine>,
    navController: NavHostController,
    context: Context
){

    LazyRow {
        items(resultado){resultado ->
            CineItem(resultado, navController, context)
        }
    }
}

@Composable
fun CineItem(
    resultado: ResultCine,
    navController: NavHostController,
    context: Context
) {
    Column (modifier = Modifier.fillMaxWidth()){
        Box(modifier = Modifier
            .width(150.dp)
            .height(230.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate(
                    AppScreens.TituloCineScreen.passData(
                        Uri.encode(resultado.id.toString()),
                        Uri.encode(resultado.poster_path),
                        Uri.encode(resultado.title),
                        Uri.encode(resultado.overview),
                        Uri.encode(resultado.release_date)
                    )
                )
            }
        ){
            AsyncImage(model = ImageRequest.Builder(context = context)
                .data("https://image.tmdb.org/t/p/w500${resultado.poster_path}")
                .crossfade(enable = true)
                .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                    contentScale = ContentScale.Crop
            )
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.BottomCenter)
                    .padding(5.dp)
            ) {
                Text(text = "${resultado.title}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 2.5.dp),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(text = "${resultado.release_date?.substring(0, 4)}",
                    modifier = Modifier.padding(start = 4.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
