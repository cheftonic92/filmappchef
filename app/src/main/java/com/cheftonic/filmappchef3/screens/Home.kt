package com.cheftonic.filmappchef3.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.cheftonic.filmappchef3.componentes.Buscador
import com.cheftonic.filmappchef3.componentes.CineLista
import com.cheftonic.filmappchef3.componentes.TvLista
import com.cheftonic.filmappchef3.model.buscador.ModelBuscador
import com.cheftonic.filmappchef3.model.cine.ModelCine
import com.cheftonic.filmappchef3.model.tv.ModelTv
import com.cheftonic.filmappchef3.repository.Repository
import com.cheftonic.filmappchef3.ui.theme.FilmAppChef3Theme
import com.cheftonic.filmappchef3.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val context = LocalContext.current

    val repository = Repository()
    val viewModel = MainViewModel(repository = repository)
    val owner: LifecycleOwner = LocalLifecycleOwner.current
    var dataBuscador by remember {
        mutableStateOf<ModelBuscador?>(null)
    }
    var tendenciasCine by remember {
        mutableStateOf<ModelCine?>(null)
    }
    var popularCine by remember {
        mutableStateOf<ModelCine?>(null)
    }
    var tendenciasTv by remember {
        mutableStateOf<ModelTv?>(null)
    }
    var popularTv by remember {
        mutableStateOf<ModelTv?>(null)
    }
    var query by remember {
        mutableStateOf("")
    }
    var isActive by remember {
        mutableStateOf(false)
    }

    try {

        viewModel.getTendenciasCine(1)
        viewModel.tendenciasCine.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                tendenciasCine = response.body()
                Log.d("HomeScreen", "$tendenciasCine")
            } else {
                Log.d("HomeScreen", "${response.code()}")
            }
        })

        viewModel.getPopularCine(1)
        viewModel.popularCine.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                popularCine = response.body()
                Log.d("HomeScreen", "$popularCine")
            } else {
                Log.d("HomeScreen", "${response.code()}")
            }
        })

        viewModel.getTendenciasTv(1)
        viewModel.tendenciasTv.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                tendenciasTv = response.body()
                Log.d("TendenciasTv", "$tendenciasTv")
            } else {
                Log.d("Error", "${response.code()}")
            }
        })

        viewModel.getPopularTv(2)
        viewModel.popularTv.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                popularTv = response.body()
                Log.d("PopularTv", "$popularTv")
            } else {
                Log.d("Fallo", "${response.code()}")
            }
        })

        viewModel.getBuscador(query, 1)
        viewModel.buscarResultados.observe(owner, Observer { response ->
            if (response.isSuccessful) {
                dataBuscador = response.body()
                Log.d("HomeScreen", "$dataBuscador")
            } else {
                Log.d("HomeScreen", "${response.code()}")
            }
        })
    } catch (e: Exception) {
        Log.d("Exception", "HomeScreen")
    }


    FilmAppChef3Theme {
        Scaffold(
            content = {
                Column(
                    modifier = Modifier
                        .padding(top = it.calculateTopPadding(), start = 18.dp, end = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(21.dp))

                    SearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        onSearch = { viewModel.getBuscador(it, 1) },
                        active = isActive,
                        onActiveChange = { isActive = !isActive },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = ""
                            )
                        },
                        placeholder = { Text(text = "Buscar título...") },
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        dataBuscador?.results?.let { it1 ->
                            Buscador(
                                resultados = it1,
                                context = context,
                                navController = navController
                            )
                        }
                    }

                    Column (modifier=Modifier.fillMaxWidth()
                        .verticalScroll(rememberScrollState())){

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(text = "PELÍCULAS",
                            modifier = Modifier.padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            letterSpacing = 1.85.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground)


                        Text(text = "Tendencias",
                            textAlign = TextAlign.Left,
                            color = MaterialTheme.colorScheme.onPrimaryContainer)

                        Spacer(Modifier.height(10.dp))

                        tendenciasCine?.results?.let { resultTendencias ->
                            CineLista(
                                resultTendencias,
                                navController,
                                context
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(text = "Populares",
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer)

                        Spacer(Modifier.height(10.dp))

                        popularCine?.results?.let { resultPopCine ->
                            CineLista(
                                resultPopCine,
                                navController,
                                context
                            )
                        }

                        Spacer(modifier = Modifier.height(25.dp))

                        Text(text = "SERIES",
                            modifier = Modifier.padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            letterSpacing = 1.85.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground)

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(text = "Tendencias",
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSecondaryContainer)

                        Spacer(Modifier.height(10.dp))

                        tendenciasTv?.results?.let { resultTendenciasTv ->
                            TvLista(
                                resultTendenciasTv,
                                navController,
                                context
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(text = "Populares",
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSecondaryContainer)

                        Spacer(Modifier.height(10.dp))

                        popularTv?.results?.let { resultPopTv ->
                            TvLista(
                                resultPopTv,
                                navController,
                                context
                            )
                        }
                        Spacer(Modifier.height(80.dp))
                    }
                }
            })
    }
}