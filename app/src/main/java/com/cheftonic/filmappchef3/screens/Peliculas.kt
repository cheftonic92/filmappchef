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
import com.cheftonic.filmappchef3.componentes.MisListasCine
import com.cheftonic.filmappchef3.model.cine.ResultCine
import com.cheftonic.filmappchef3.repository.Repository
import com.cheftonic.filmappchef3.ui.theme.FilmAppChef3Theme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun PeliculasScreen(navController: NavHostController) {
    val repository = Repository()
    val coroutineScope = rememberCoroutineScope()

    var misPeliculasFav by remember {
        mutableStateOf<List<ResultCine>>(emptyList())
    }
    var misPeliculasVistas by remember {
        mutableStateOf<List<ResultCine>>(emptyList())
    }

    var misPeliculasPendientes by remember {
        mutableStateOf<List<ResultCine>>(emptyList())
    }

    val context = LocalContext.current

    Log.d("Launch", "Launcheffect funciona")

    LaunchedEffect(key1 = "fetch_movies") {
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val pelisFav = FirebaseFirestore.getInstance()
                .collection("Usuarios")
                .document(userId)
                .collection("Peliculas")
                .whereEqualTo("peli_fav", true)
                .get()
                .await()

            val movieIds = pelisFav.mapNotNull { it.id.toIntOrNull() }

            val movies = mutableListOf<ResultCine>()
            movieIds.forEach { id ->
                try {
                    val response = repository.getPelicula(id)
                    if (response.isSuccessful) {
                        response.body()?.let { movieDetails ->

                            movies.add(movieDetails)
                            Log.d("misPeliculas", "${response}")
                            Log.d("pelis_vm_movies", "${movies}")
                            Log.d("pelis_vm_movieDetails", "${movieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            misPeliculasFav = movies
        }
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val pelisVistas =
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(userId)
                    .collection("Peliculas")
                    .whereEqualTo("peli_vistas", true)
                    .get()
                    .await()

            val movieIds = pelisVistas.mapNotNull { it.id.toIntOrNull() }

            val movies = mutableListOf<ResultCine>()
            movieIds.forEach { id ->
                try {
                    val response = repository.getPelicula(id)
                    if (response.isSuccessful) {
                        response.body()?.let { movieDetails ->
                            movies.add(movieDetails)
                            Log.d("misPeliculas", "${response}")
                            Log.d("pelis_vm_movies", "${movies}")
                            Log.d("pelis_vm_movieDetails", "${movieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            misPeliculasVistas = movies
        }
        coroutineScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val pelisPendientes =
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(userId)
                    .collection("Peliculas")
                    .whereEqualTo("peli_pendientes", true)
                    .get()
                    .await()

            val movieIds = pelisPendientes.mapNotNull { it.id.toIntOrNull() }

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

                    val peliVoto = docSnapshot.getLong("peli_voto")?.toInt()
                        ?: 0 // or handle nulls as you see fit
                    Log.d("peliVoto", "${peliVoto}")
                    val response = repository.getPelicula(id)
                    if (response.isSuccessful) {
                        response.body()?.let { movieDetails ->
                            val updatedMovieDetails = movieDetails.copy(peli_voto = peliVoto)
                            movies.add(updatedMovieDetails)
                            Log.d("misPeliculas", "${response}")
                            Log.d("pelis_vm_movies", "${movies}")
                            Log.d("pelis_vm_movieDetails", "${movieDetails}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Screen", "Error fetching movie ID $id: ${e.localizedMessage}")
                }
            }
            misPeliculasPendientes = movies
        }
    }

    FilmAppChef3Theme {

            Scaffold(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                content = {
                    Column(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding(), start = 18.dp, end = 18.dp)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            text = "MIS PELICULAS",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 42.sp,
                            letterSpacing = 2.5.sp,
                            lineHeight = 40.sp,
                            fontWeight = FontWeight.Bold
                        )

                        if (misPeliculasPendientes.isNotEmpty()) {

                            Text(text = "MIS PENDIENTES")

                            MisListasCine(
                                misPeliculasPendientes,
                                navController,
                                LocalContext.current
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                        } else {
                            // Consider showing a loading or empty placeholder
                            Text("No hay peliculas pendientes")
                            Spacer(modifier = Modifier.height(28.dp))
                        }

                        if (misPeliculasFav.isNotEmpty()) {
                            Text(text = "MIS FAVORITAS")

                            MisListasCine(misPeliculasFav, navController, LocalContext.current)

                            Spacer(modifier = Modifier.height(28.dp))

                        } else {
                            // Consider showing a loading or empty placeholder
                            Text("No hay peliculas favoritas")
                            Spacer(modifier = Modifier.height(28.dp))
                        }
                        if (misPeliculasVistas.isNotEmpty()) {

                            Text(text = "MIS VISTAS")

                            MisListasCine(misPeliculasVistas, navController, LocalContext.current)

                        } else {
                            Text("No hay peliculas vistas")
                        }

                        Spacer(modifier = Modifier.height(250.dp))
                    }
                })
        }
    }

