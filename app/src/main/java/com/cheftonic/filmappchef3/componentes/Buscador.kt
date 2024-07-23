package com.cheftonic.filmappchef3.componentes

import android.content.Context
import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cheftonic.filmappchef3.model.buscador.ResultBuscador
import com.cheftonic.filmappchef3.navigation.AppScreens
import java.util.Locale

@Composable
fun Buscador(
    resultados: List<ResultBuscador>,
    context: Context,
    navController: NavHostController
) {
    LazyColumn {
        items(resultados) {
            ResultadoBusqueda(resultado = it, context, navController)
        }
    }
}

@Composable
fun ResultadoBusqueda(
    resultado: ResultBuscador,
    context: Context,
    navController: NavHostController
) {

    if (resultado.poster_path!=null) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Imagen del resultado
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(237.dp)
                        .clickable {
                            if (resultado.media_type == "movie") {
                                navController.navigate(
                                    AppScreens.TituloCineScreen.passData(
                                        Uri.encode(resultado.id.toString()),
                                        Uri.encode(resultado.poster_path),
                                        Uri.encode(resultado.title),
                                        Uri.encode(resultado.overview),
                                        Uri.encode(resultado.release_date)
                                    )
                                )
                            } else {
                                navController.navigate(
                                    AppScreens.TituloTvScreen.passData(
                                        Uri.encode(resultado.id.toString()),
                                        Uri.encode(resultado.poster_path),
                                        Uri.encode(resultado.name),
                                        Uri.encode(resultado.overview),
                                        Uri.encode(resultado.first_air_date)
                                    )
                                )
                            }
                        }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = context)
                            .data("https://image.tmdb.org/t/p/w500${resultado.poster_path}")
                            .crossfade(enable = true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp)
                ) {
                    if (resultado.media_type == "movie") {
                        Text(text = "${resultado.title}")
                        Spacer(modifier = Modifier.height(17.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${resultado.release_date?.substring(0, 4)}",
                                fontSize = 10.sp
                            )
                            Text(text = resultado.media_type.uppercase(Locale.getDefault()))
                        }

                        Text(
                            text = resultado.overview,
                            maxLines = 5,
                            overflow = TextOverflow.Visible
                        )
                    } else {
                        Text(text = "${resultado.name}")
                        Spacer(modifier = Modifier.height(17.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${resultado.first_air_date?.substring(0, 4)}",
                                fontSize = 10.sp
                            )
                            Text(text = resultado.media_type.uppercase(Locale.getDefault()))
                        }
                        Text(
                            text = resultado.overview,
                            maxLines = 5,
                            overflow = TextOverflow.Visible
                        )
                    }
                }
            }
        }
    }
}