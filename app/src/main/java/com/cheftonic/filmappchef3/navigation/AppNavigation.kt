package com.cheftonic.filmappchef3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cheftonic.filmappchef3.screens.HomeScreen
import com.cheftonic.filmappchef3.screens.LoginScreen
import com.cheftonic.filmappchef3.screens.PeliculasScreen
import com.cheftonic.filmappchef3.screens.RankingScreen
import com.cheftonic.filmappchef3.screens.SeriesScreen
import com.cheftonic.filmappchef3.screens.TituloCineScreen
import com.cheftonic.filmappchef3.screens.TituloTvScreen

@Composable
fun AppNavigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = AppScreens.Login.route){
        composable(AppScreens.Login.route){
            LoginScreen(navController)
        }
        composable(route = AppScreens.Home.route){
            HomeScreen(navController)
        }
        composable(route = AppScreens.Peliculas.route){
            PeliculasScreen(navController)
        }
        composable(route = AppScreens.Series.route){
            SeriesScreen(navController)
        }
        composable(route = AppScreens.Ranking.route){
            RankingScreen(navController)
        }
        composable(route = AppScreens.TituloCineScreen.route,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                },
                navArgument("poster"){
                    type = NavType.StringType
                },
                navArgument("titulo"){
                    type = NavType.StringType
                },
                navArgument("sinopsis"){
                    type = NavType.StringType
                },
                navArgument("fecha"){
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString("id")
            val poster = it.arguments?.getString("poster")
            val titulo = it.arguments?.getString("titulo")
            val sinopsis = it.arguments?.getString("sinopsis")
            val fecha = it.arguments?.getString("fecha")

            TituloCineScreen(navController, id, poster, titulo, sinopsis, fecha)
        }

        composable(route = AppScreens.TituloTvScreen.route,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType},
                navArgument("poster"){
                    type = NavType.StringType
                },
                navArgument("titulo"){
                    type = NavType.StringType
                },
                navArgument("sinopsis"){
                    type = NavType.StringType
                },
                navArgument("fecha"){
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString("id")
            val poster = it.arguments?.getString("poster")
            val titulo = it.arguments?.getString("titulo")
            val sinopsis = it.arguments?.getString("sinopsis")
            val fecha = it.arguments?.getString("fecha")
            TituloTvScreen(navController, id, poster, titulo, sinopsis, fecha)
        }
    }
}