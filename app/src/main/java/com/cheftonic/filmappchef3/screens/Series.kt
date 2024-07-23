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
import com.cheftonic.filmappchef3.componentes.MisListasTv
import com.cheftonic.filmappchef3.model.tv.ResultTv
import com.cheftonic.filmappchef3.repository.Repository
import com.cheftonic.filmappchef3.ui.theme.FilmAppChef3Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SeriesScreen(navController: NavHostController) {
    val repository = Repository()
    val coroutineScope = rememberCoroutineScope()
    var misSeriesFav by remember {
        mutableStateOf<List<ResultTv>>(emptyList())
    }
    var misSeriesVistas by remember {
        mutableStateOf<List<ResultTv>>(emptyList())
    }

    var misSeriesPendientes by remember {
        mutableStateOf<List<ResultTv>>(emptyList())
    }
    LaunchedEffect(key1 = "fetch_movies") {
        Log.d("Launch", "Launcheffect empieza")
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val seriesFav = FirebaseFirestore.getInstance()
                .collection("Usuarios")
                .document(userId)
                .collection("Series")
                .whereEqualTo("serie_fav", true)
                .get()
                .await()

            val serieIds = seriesFav.mapNotNull { it.id.toIntOrNull() }

            val series = mutableListOf<ResultTv>()
            serieIds.forEach { id ->
                try {
                    val response = repository.getMiSerie(id)
                    if (response.isSuccessful) {
                        response.body()?.let { serieDetails ->

                            series.add(serieDetails)
                            Log.d("misSeries", "${response}")
                            Log.d("misSeries", "${series}")
                            Log.d("misSeries", "${serieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching serie ID $id: ${e.localizedMessage}")
                }
            }
            misSeriesFav = series
        }
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val seriesVistas =
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(userId)
                    .collection("Series")
                    .whereEqualTo("serie_vistas", true)
                    .get()
                    .await()

            val serieIds = seriesVistas.mapNotNull { it.id.toIntOrNull() }

            val series = mutableListOf<ResultTv>()
            serieIds.forEach { id ->
                try {
                    val response = repository.getMiSerie(id)
                    if (response.isSuccessful) {
                        response.body()?.let { serieDetails ->
                            series.add(serieDetails)
                            Log.d("misSeries", "${response}")
                            Log.d("misSeries", "${series}")
                            Log.d("misSeries", "${serieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            misSeriesVistas = series
        }
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val seriesPendientes =
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(userId)
                    .collection("Series")
                    .whereEqualTo("serie_pendientes", true)
                    .get()
                    .await()

            val serieIds = seriesPendientes.mapNotNull { it.id.toIntOrNull() }

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
                            Log.d("misSeries", "${series}")
                            Log.d("misSeries", "${serieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            misSeriesPendientes = series
        }
    }

    FilmAppChef3Theme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            content = {
                Column(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding(), start = 18.dp, end = 18.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "MIS SERIES",
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 42.sp,
                        letterSpacing = 2.5.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.Bold)

                    if (misSeriesPendientes.isNotEmpty()) {

                        Text(text = "MIS PENDIENTES",
                                textAlign = TextAlign.Start)

                            MisListasTv(misSeriesPendientes, navController, LocalContext.current)

                            Spacer(modifier = Modifier.height(28.dp))

                    } else {
                        Text("No hay series pendientes")
                        Spacer(modifier = Modifier.height(28.dp))
                    }

                    if (misSeriesFav.isNotEmpty()) {
                        Text(
                                text = "MIS FAVORITAS",
                                textAlign = TextAlign.Start
                            )

                            MisListasTv(misSeriesFav, navController, LocalContext.current)

                            Spacer(modifier = Modifier.height(28.dp))

                    } else {
                        Text("No hay series favoritas")
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                    if (misSeriesVistas.isNotEmpty()) {

                        Text(
                                text = "MIS VISTAS",
                                textAlign = TextAlign.Start
                            )
                            MisListasTv(misSeriesVistas, navController, LocalContext.current)

                    } else {
                        Text("No hay series vistas")
                        Spacer(modifier = Modifier.height(28.dp))
                    }
                    Spacer(modifier = Modifier.height(250.dp))
                }
            })
    }
}