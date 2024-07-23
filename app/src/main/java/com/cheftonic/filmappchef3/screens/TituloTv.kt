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
import androidx.compose.material3.TopAppBarDefaults
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
import com.cheftonic.filmappchef3.componentes.CreadoresTv
import com.cheftonic.filmappchef3.componentes.MisListasTv
import com.cheftonic.filmappchef3.componentes.RatingBar
import com.cheftonic.filmappchef3.componentes.Reparto
import com.cheftonic.filmappchef3.model.credits.ModelCredits
import com.cheftonic.filmappchef3.model.tv.DetallesTv
import com.cheftonic.filmappchef3.model.tv.ModelTv
import com.cheftonic.filmappchef3.model.video.ModelVideo
import com.cheftonic.filmappchef3.repository.Repository
import com.cheftonic.filmappchef3.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TituloTvScreen(
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

    Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    text = "${titulo}",
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    fontSize = 18.sp
                )
            },colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
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
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp)
            ) {
                TituloTv(
                    navController = navController,
                    id = id,
                    poster = poster,
                    titulo = titulo,
                    sinopsis = sinopsis,
                    fecha = fecha,
                    context = context
                )
            }
        })
}

@Composable
fun TituloTv(
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

    val tvId = id!!.toInt()

    val serieId = id.toString()

    val userDocRef = user_id?.let {
        FirebaseFirestore.getInstance().collection("Usuarios")
            .document(user_id)
    }

    val serieDocRef = serieId?.let {
        FirebaseFirestore.getInstance()
            .collection("Usuarios")
            .document(user_id)
            .collection("Series")
            .document(serieId)}

    data class Serie(
        val serie_fav: Boolean?,
        val serie_pendientes: Boolean?,
        val serie_vistas: Boolean?,
        val serie_voto: Int?
    )

    val serieData = Serie(
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

        serieDocRef?.get()?.addOnSuccessListener { document ->

            if (document != null && document.exists()) {

                vistas = document.getBoolean("serie_vistas") ?: false
                pendientes = document.getBoolean("serie_pendientes") ?: false
                favs = document.getBoolean("serie_fav") ?: false
                voto = document.getLong("serie_voto")?.toInt() ?: 0
                Log.d("Firestore", "acceso")
                Log.d("voto", "voto anterior: ${voto}")

            } else {
                FirebaseFirestore.getInstance()
                    .collection("Usuarios")
                    .document(user_id)
                    .collection("Series")
                    .document(serieId)
                    .set(serieData)
                Log.d("Firestore", "Creado Nuevo documento")
            }
        }?.addOnFailureListener { exception ->
            // Handle errors
            Log.e("Firestore", "Error", exception)
        }
    }

    val repository = Repository()
    val viewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current

    var serie by remember {
        mutableStateOf<DetallesTv?>(null)
    }
    var repartoSerie by remember {
        mutableStateOf<ModelCredits?>(null)
    }
    var trailerTv by remember {
        mutableStateOf<ModelVideo?>(null)
    }
    var recoTv by remember {
        mutableStateOf<ModelTv?>(null)
    }



    try {
        viewModel.getRepartoTv(tvId)
        viewModel.repartoTv.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                repartoSerie = response.body()
                Log.d("Actores", "${repartoSerie}")
            } else {
                Log.d("error", "${response.code()}")
            }
        })

        viewModel.getTrailerTv(tvId)
        viewModel.trailerTv.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                trailerTv = response.body()
                Log.d("Trailer", "${trailerTv}")
            } else {
                Log.d("Trailer", "${response.code()}")
            }
        })

        viewModel.getSerie(tvId)
        viewModel.serie.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                serie = response.body()
                Log.d("Creador", "${serie}")
            } else {
                Log.d("Creador", "${response.code()}")
            }
        })

        viewModel.getRecoTv(tvId)
        viewModel.recoTv.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                recoTv = response.body()
                Log.d("Reco", "$recoTv")
            } else {
                Log.d("Trailer", "${response.code()}")
            }
        })

    } catch (e: Exception) {
        Log.d("Exception", "HomeScreen")
    }

    fun updateSerieFav() {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_fav", true)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Fav actualizado")
                // Actualizar favs
                favs = true
            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Has añadido esta serie a tu lista de favoritas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun borrarSerieFav() {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_fav", false)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Fav borrado")
                // Actualizar favs
                favs = false
            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Has eliminado esta serie de tu lista de favoritas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun updateSerieVista() {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_vistas", true)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Vista actualizado")
                // Actualizar vistas
                vistas = true
            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Has añadido esta serie a tu lista de vistas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun borrarSerieVista() {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_vistas", false)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Vista borrado")
                // Actualizar vistas
                vistas = false
            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Has eliminado esta serie de tu lista de vistas",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun updateSeriePendientes() {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_pendientes", true)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Pendiente actualizado")
                // Actualizar pendiente
                pendientes = true
            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Has añadido esta serie a tu lista de pendientes",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun borrarSeriePendientes() {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_pendientes", false)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Pendiente actualizado")
                //actualizar pendiente
                pendientes = false
            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Has eliminado esta serie de tu lista de pendientes",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    fun updateSerieVoto(newVal: Int) {

        userDocRef?.collection("Series")
            ?.document(serieId)
            ?.update("serie_voto", newVal)
            ?.addOnSuccessListener {
                Log.d("Firestore", "Voto actualizado")
                // Actualizar voto
                voto = newVal

            }
            ?.addOnFailureListener { exception ->
                Log.e("Firestore", "Error", exception)
            }
        Toast.makeText(
            context,
            "Se ha actualizado tu voto a ${newVal}",
            Toast.LENGTH_SHORT
        )
            .show()
    }


    // Main content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        verticalArrangement = Arrangement.Top
    ) {
        // Poster de la serie
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
                    trailerTv?.results?.let { key ->
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

        // Detalles de la serie
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Text(text = "${titulo}", fontSize = 27.sp)
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        text = "${fecha?.substring(0, 4)}",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "${serie?.number_of_seasons} Temporadas",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            if (!pendientes) {
                                if (!vistas) {
                                    updateSerieVista()
                                } else {
                                    vistas
                                    borrarSerieVista()
                                    updateSerieVoto(0)
                                    if (favs) {
                                        borrarSerieFav()
                                    }
                                }
                            } else {
                                pendientes
                                updateSerieVista()
                                borrarSeriePendientes()
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
                                    updateSeriePendientes()
                                    updateSerieVoto(0)
                                    if (favs) {
                                        borrarSerieFav()
                                    }
                                } else {
                                    pendientes
                                    borrarSeriePendientes()
                                }
                            } else {
                                vistas
                                updateSeriePendientes()
                                borrarSerieVista()
                                updateSerieVoto(0)
                                if (favs) {
                                    borrarSerieFav()
                                }
                            }
                        }
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
                                    updateSerieFav()
                                } else {
                                    favs
                                    borrarSerieFav()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "No puedes agregar una serie a favoritas sin haberla visto",
                                    Toast.LENGTH_SHORT
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

            if (vistas) {

                LaunchedEffect(voto) {
                    //Acceder al voto actual del titulo
                    serieDocRef?.get()?.addOnSuccessListener { document ->

                        if (document != null && document.exists()) {
                            voto = document.getLong("serie_voto")?.toInt() ?: 0
                        }
                        Log.d("Voto", "Voto actual: $voto")
                    }
                }

                Text(text = "Valora esta serie", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(18.dp))

                RatingBar(
                    maxVal = 10,
                    iniVal = voto,
                    onValChange = { newVal -> updateSerieVoto(newVal) }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            Column {
                Text(text = "Sinopsis:", fontSize = 18.sp)
                Text(text = "${sinopsis}", fontSize = 14.5.sp)
            }

            Spacer(Modifier.height(25.dp))

            //Creador de serie
            Column {
                Text(
                    text = "Creada por:",
                    fontSize = 18.sp
                )
                serie?.created_by?.let { name ->
                    val creadores = name.filter { it.name !== null }
                    CreadoresTv(
                        resultado = creadores,
                        navController = navController,
                        context = context
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Reparto:", fontSize = 18.sp)
            repartoSerie?.cast?.let { resultCreditsTv ->
                Reparto(
                    resultado = resultCreditsTv,
                    navController = navController,
                    context = context
                )
            }


            Spacer(Modifier.height(25.dp))

            Text(text = "Quizás te gusten...")

            recoTv?.results?.let { resultReco ->
                MisListasTv(
                    resultReco,
                    navController,
                    context
                )
            }

            Spacer(modifier = Modifier.height(150.dp))
        }
    }
}