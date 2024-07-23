package com.cheftonic.filmappchef3.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cheftonic.filmappchef3.componentes.Directores
import com.cheftonic.filmappchef3.componentes.MisListasCine
import com.cheftonic.filmappchef3.componentes.RatingBar
import com.cheftonic.filmappchef3.componentes.Reparto
import com.cheftonic.filmappchef3.model.cine.ModelCine
import com.cheftonic.filmappchef3.model.credits.ModelCredits
import com.cheftonic.filmappchef3.model.video.ModelVideo
import com.cheftonic.filmappchef3.repository.Repository
import com.cheftonic.filmappchef3.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TituloCineScreen(
    navController: NavHostController,
    id: String?,
    poster: String?,
    titulo: String?,
    sinopsis: String?,
    fecha: String?
) {
    val repository = Repository()
    val viewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "$titulo",
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 18.sp
                )

            }, colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = ""
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp)
            ){
                TituloCine(
                    navController = navController,
                    id = id,
                    poster = poster,
                    titulo = titulo,
                    sinopsis = sinopsis,
                    fecha = fecha,
                    context = context
                )

                Spacer(modifier = Modifier.height(30.dp))

            }

        }
    )
}

@Composable
fun TituloCine(
    navController: NavHostController,
    id: String?,
    poster: String?,
    titulo: String?,
    sinopsis: String?,
    fecha: String?,
    context: Context
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    val user_id = userId.toString()

    val movieId = id!!.toInt()

    val peliculaId = id.toString()

    val userDocRef = user_id.let {
        FirebaseFirestore.getInstance()
            .collection("Usuarios")
            .document(user_id)
    }

    val peliDocRef = peliculaId.let {
        FirebaseFirestore.getInstance()
            .collection("Usuarios")
            .document(user_id)
            .collection("Peliculas")
            .document(peliculaId)
    }

    data class Pelicula(
        val peli_fav: Boolean?,
        val peli_pendientes: Boolean?,
        val peli_vistas: Boolean?,
        val peli_voto: Int?
    )

    val peliData = Pelicula(
        false,
        false,
        false,
        0
    )

    var vistas by remember { mutableStateOf(false) }
    var pendientes by remember { mutableStateOf(false) }
    var favs by remember { mutableStateOf(false) }
    var voto by remember { mutableStateOf(0) }

    LaunchedEffect(userId) {

        peliDocRef.get().addOnSuccessListener { document ->

            if (document != null && document.exists()) {

                vistas = document.getBoolean("peli_vistas") ?: false
                pendientes = document.getBoolean("peli_pendientes") ?: false
                favs = document.getBoolean("peli_fav") ?: false
                voto = document.getLong("peli_voto")?.toInt() ?: 0
                Log.d("Firestore", "acceso")
                Log.d("voto", "previous voto: ${voto}")

            } else {
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(user_id).collection("Peliculas")
                    .document(peliculaId)
                    .set(peliData)
                Log.d("Firestore", "Creado Nuevo documento no existente")
            }
        }.addOnFailureListener { exception ->
            // Handle errors
            Log.e("Firestore", "Error getting document", exception)
        }
    }

    val repository = Repository()
    val viewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current

    var directorCine by remember {
        mutableStateOf<ModelCredits?>(null)
    }
    var repartoCine by remember {
        mutableStateOf<ModelCredits?>(null)
    }
    var trailerCine by remember {
        mutableStateOf<ModelVideo?>(null)
    }
    var recoCine by remember {
        mutableStateOf<ModelCine?>(null)
    }

    try {
        viewModel.getCreditosCine(movieId)
        viewModel.creditosCine.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                directorCine = response.body()
                Log.d("HomeScreen", "$directorCine")
            } else {
                Log.d("error", "${response.code()}")
            }
        })
        viewModel.creditosCine.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                repartoCine = response.body()
                Log.d("Actores", "$repartoCine")
            } else {
                Log.d("error", "${response.code()}")
            }
        })
        viewModel.getTrailerCine(movieId)
        viewModel.trailerCine.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                trailerCine = response.body()
                Log.d("Trailer", "$trailerCine")
            } else {
                Log.d("Trailer", "${response.code()}")
            }
        })
        viewModel.getRecoCine(movieId)
        viewModel.recoCine.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                recoCine = response.body()
                Log.d("Trailer", "$recoCine")
            } else {
                Log.d("Trailer", "${response.code()}")
            }
        })
    } catch (e: Exception) {
        Log.d("Exception", "HomeScreen")
    }

    fun updatePeliFav() {

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            .update("peli_fav", true)
            .addOnSuccessListener {1
                Log.d("Firestore", "Fav updated successfully")
                // Actualizar favs
                favs = true
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating fav", exception)
            }

        Toast.makeText(
            context,
            "Has añadido esta película a tu lista de favoritas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun borrarPeliFav() {

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            .update("peli_fav", false)
            .addOnSuccessListener {
                Log.d("Firestore", "Fav updated successfully")
                // Actualizar favs
                favs = false
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating fav", exception)
            }
        Toast.makeText(
            context,
            "Has eliminado esta película de tu lista de favoritas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun updatePeliVista() {

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            .update("peli_vistas", true)
            .addOnSuccessListener {
                Log.d("Firestore", "Vista updated successfully")
                // Actualizar vistas
                vistas = true
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating vista", exception)
            }
        Toast.makeText(
            context,
            "Has añadido esta película a tu lista de vistas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun borrarPeliVista() {

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            .update("peli_vistas", false)
            .addOnSuccessListener {
                Log.d("Firestore", "Fav updated successfully")
                // Actualizar vistas
                vistas = false
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating fav", exception)
            }
        Toast.makeText(
            context,
            "Has eliminado esta película de tu lista de vistas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun updatePeliPendientes() {

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            .update("peli_pendientes", true)
            .addOnSuccessListener {
                Log.d("Firestore", "Pendiente updated successfully")
                // Actualizar pendientes
                pendientes = true
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating pendiente", exception)
            }
        Toast.makeText(
            context,
            "Has añadido esta película a tu lista de pendientes",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun borrarPeliPendientes() {

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            .update("peli_pendientes", false)
            .addOnSuccessListener {
                Log.d("Firestore", "Fav updated successfully")
                // Actualizar pendientes
                pendientes = false
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error updating pendientes", exception)
            }
        Toast.makeText(
            context,
            "Has eliminado esta película de tu lista de pendientes",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun updatePeliVoto(newVal: Int) {

        //Acceder a la coleccion Películas y al documento correspondiente a la pelicula

        userDocRef.collection("Peliculas")
            .document(peliculaId)
            //Establecer el campo peli_voto a la nueva valoracion
            .update("peli_voto", newVal)
            .addOnSuccessListener {

                Log.d("Firestore", "Voto actualizado")
                // Actualizar el nuevo voto
                voto = newVal

            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error actualizando voto", exception)
            }
        Toast.makeText(
            context,
            "Se ha actualizado tu voto a ${newVal}",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    // Detalles de la película

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Top
    ) {
        // Poster de la pelicula
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data("https://image.tmdb.org/t/p/w500${poster}")
                    .crossfade(enable = true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(228.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            // Botón de reproducción
            IconButton(
                onClick = {
                    //Acceder al primer trailer y redirigir a youtube
                    trailerCine?.results?.let { key ->
                        val trailers = key.filter { it.key != null }
                        Log.d("Trailers", "$trailers")

                        val trailerKey = trailers.first().key

                        val trailerUrl = "https://www.youtube.com/watch?v=${trailerKey}"

                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayCircleOutline,
                    contentDescription = "Reproducir tráiler",
                    tint = Color.White,
                    modifier = Modifier.size(125.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text(text = "$titulo", fontSize = 27.sp)
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${fecha?.substring(0, 4)}",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start
                )
                Row {
                    IconButton(
                        onClick = {
                            if (!pendientes) {
                                if (!vistas) {
                                    updatePeliVista()
                                } else{
                                    vistas
                                    borrarPeliVista()
                                    if(voto != 0) {
                                        updatePeliVoto(0)
                                    }
                                    if(favs){
                                    borrarPeliFav()
                                        }
                                }
                            } else {
                                pendientes
                                updatePeliVista()
                                borrarPeliPendientes()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.RemoveRedEye,
                            contentDescription = "Marcar Vista",
                            tint = if (vistas) Color.Green else Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = {
                            if (!vistas) {
                                if (!pendientes) {
                                    updatePeliPendientes()
                                } else {
                                    pendientes
                                    borrarPeliPendientes()
                                }
                            } else if (vistas) {
                                updatePeliPendientes()
                                borrarPeliVista()
                                if (favs) {
                                    borrarPeliFav()
                                }
                                if(voto != 0) {
                                    updatePeliVoto(0)
                                }
                            } else{
                                pendientes
                                borrarPeliPendientes()
                            }
                        },

                        ) {
                        Icon(
                            imageVector = Icons.Filled.Bookmark,
                            contentDescription = "Marcar Pendiente",
                            tint = if (pendientes) Color.Yellow else Color.White,
                            modifier = Modifier.size(25.dp)

                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = {
                            if (vistas) {
                                if (!favs) {
                                    updatePeliFav()
                                } else{
                                    favs
                                    borrarPeliFav()
                                }
                            }
                            else{
                                Toast.makeText(
                                context,
                                "No puedes agregar una pelicula a favoritas sin haberla visto",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Marcar Favorita",
                            tint = if (favs) Color.Red else Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Column {
                //Rating Bar

                if(vistas) {

                    LaunchedEffect(voto) {
                        //Acceder al voto actual del titulo
                        peliDocRef.get().addOnSuccessListener { document ->

                            if (document != null && document.exists()) {
                                voto = document.getLong("peli_voto")?.toInt() ?: 0
                            }
                            Log.d("Voto", "Voto actual: $voto")
                        }
                    }

                    Text(text = "Valora esta película", fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(18.dp))

                    RatingBar(
                        maxVal = 10,
                        iniVal = voto,
                        onValChange = { newVal -> updatePeliVoto(newVal) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

            Column {
                Text(text = "Sinopsis:", fontSize = 18.sp)
                Text(text = "$sinopsis", fontSize = 14.5.sp)
            }

            Spacer(Modifier.height(25.dp))

                Text(
                    text = "Dirigida por:",
                    fontSize = 18.sp
                )
                directorCine?.crew?.let { crew ->
                    val directores = crew.filter { it.job == "Director" }
                    Directores(
                        resultado = directores,
                        navController = navController,
                        context = context
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Reparto:", fontSize = 18.sp)

            repartoCine?.cast?.let { resultCreditsCines ->
                Reparto(
                    resultado = resultCreditsCines,
                    navController = navController,
                    context = context
                )
            }

            Spacer(Modifier.height(25.dp))

            Text(text = "Quizás te gusten...")

            recoCine?.results?.let { resultReco ->
                MisListasCine(
                    resultReco,
                    navController,
                    context
                )
            }
            Spacer(Modifier.height(150.dp))
        }
    }
}

