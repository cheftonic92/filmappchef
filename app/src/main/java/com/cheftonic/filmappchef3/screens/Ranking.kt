package com.cheftonic.filmappchef3.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cheftonic.filmappchef3.componentes.MiRankingCine
import com.cheftonic.filmappchef3.componentes.MiRankingTv
import com.cheftonic.filmappchef3.model.cine.ResultCine
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun RankingScreen(navController: NavHostController) {

    val repository = Repository()
    val coroutineScope = rememberCoroutineScope()
    var miRankingCine by remember {
        mutableStateOf<List<ResultCine>>(emptyList())
    }
    var miRankingSerie by remember {
        mutableStateOf<List<ResultTv>>(emptyList())
    }

    Log.d("Launch", "Dentro")

    LaunchedEffect(key1 = "fetch_movies") {
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val pelisFav = FirebaseFirestore.getInstance()
                .collection("Usuarios")
                .document(userId)
                .collection("Peliculas")
                .orderBy("peli_voto", Query.Direction.DESCENDING).limit(10)
                .get()
                .await()

            val movieIds = pelisFav.mapNotNull { it.id.toIntOrNull() }

            val movies = mutableListOf<ResultCine>()
            movieIds.forEach { id ->
                try {
                    val docSnapshot = FirebaseFirestore.getInstance()
                        .collection("Usuarios")
                        .document(userId)
                        .collection("Peliculas")
                        .document(id.toString())
                        .get()
                        .await()

                    // Fetch peli_voto
                    val peliVoto = docSnapshot.getLong("peli_voto")?.toInt() ?: 0
                    Log.d("peliVoto", "${peliVoto}")
                    val response = repository.getPelicula(id)
                    if (response.isSuccessful) {
                        response.body()?.let { movieDetails ->
                            val updatedMovieDetails = movieDetails.copy(peli_voto = peliVoto)
                            movies.add(updatedMovieDetails)

                            Log.d("misPeliculas", "${response}")
                            Log.d("pelis_vm_movies", "${movies}")
                            Log.d("pelis_vm_movieDetails", "${movieDetails}")
                            Log.d("updatedmovieDetails", "${updatedMovieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            miRankingCine = movies
        }
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val misSeries =
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(userId)
                    .collection("Series")
                    .orderBy("serie_voto", Query.Direction.DESCENDING).limit(10)
                    .get()
                    .await()

            val serieIds = misSeries.mapNotNull { it.id.toIntOrNull() }

            val series = mutableListOf<ResultTv>()
            serieIds.forEach { id ->
                try {
                    val docSnapshot = FirebaseFirestore.getInstance()
                        .collection("Usuarios")
                        .document(userId)
                        .collection("Series")
                        .document(id.toString())
                        .get()
                        .await()

                    val serieVoto = docSnapshot.getLong("serie_voto")?.toInt() ?: 0
                    Log.d("serieVoto", "${serieVoto}")
                    val response = repository.getMiSerie(id)
                    if (response.isSuccessful) {
                        response.body()?.let { serieDetails ->
                            val updatedSerieDetails = serieDetails.copy(serie_voto = serieVoto)
                            series.add(updatedSerieDetails)

                            Log.d("misSeries", "${response}")
                            Log.d("Series_vm_movies", "${series}")
                            Log.d("Series_vm_movieDetails", "${serieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            miRankingSerie = series
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        content = {
            Column(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding(), start = 18.dp, end = 18.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "MIS RANKINGS",
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 42.sp,
                    letterSpacing = 2.5.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    if (miRankingCine.isNotEmpty()) {

                        Text(
                            text = "PELICULAS",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                            letterSpacing = 1.85.sp,
                            fontWeight = FontWeight.Bold
                        )

                        MiRankingCine(miRankingCine, navController, LocalContext.current)

                    } else {
                        Text("No hay peliculas votadas")
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                    if (miRankingSerie.isNotEmpty()) {

                        Spacer(modifier = Modifier.height(28.dp))

                        Text(
                            text = "SERIES",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                            letterSpacing = 1.85.sp,
                            fontWeight = FontWeight.Bold
                        )

                        MiRankingTv(miRankingSerie, navController, LocalContext.current)

                    } else {
                        Text("No hay Series Votadas")
                    }
                }
                Spacer(modifier = Modifier.height(250.dp))
            }
        })
}
