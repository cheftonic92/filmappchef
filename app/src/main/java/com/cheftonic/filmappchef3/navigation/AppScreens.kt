package com.cheftonic.filmappchef3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMovies
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(val route: String, val title: String, val icon: ImageVector){
    object Login: AppScreens("login", "Login", Icons.Default.Home)
    object Home: AppScreens("home", "Home", Icons.Default.Home)
    object Peliculas: AppScreens("peliculas", "Mis Peliculas", Icons.Default.LocalMovies)
    object Series: AppScreens("series", "Mis Series", Icons.Default.Tv)
    object Ranking: AppScreens("ranking", "Mis Rankings", Icons.Default.StackedBarChart)


    object TituloCineScreen: AppScreens("detallepeli/{id}/{poster}/{titulo}/{sinopsis}/{fecha}",
        "Titulo Cine", Icons.Default.LocalMovies){
        fun passData(
            id: String?,
            poster: String?,
            titulo: String?,
            sinopsis: String?,
            fecha: String?

        ): String {
            return "detallepeli/$id/$poster/$titulo/$sinopsis/$fecha"
        }
    }


    object TituloTvScreen: AppScreens("detalleserie/{id}/{poster}/{titulo}/{sinopsis}/{fecha}",
        "Titulo Tv", Icons.Default.Tv){
        fun passData(
            id: String?,
            poster: String?,
            titulo: String?,
            sinopsis: String?,
            fecha: String?
        ): String {
            return "detalleserie/$id/$poster/$titulo/$sinopsis/$fecha"
        }
    }
}