package com.cheftonic.filmappchef3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cheftonic.filmappchef3.navigation.AppNavigation
import com.cheftonic.filmappchef3.navigation.AppScreens
import com.cheftonic.filmappchef3.ui.theme.FilmAppChef3Theme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            FilmAppChef3Theme {
                // Un contenedor de superficie que utiliza el color 'background' del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold (
                        bottomBar = {BottomBar(navController)},
                        content = {
                            AppNavigation(navController = navController)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {12
    val screens = listOf(
        AppScreens.Home,
        AppScreens.Peliculas,
        AppScreens.Series,
        AppScreens.Ranking
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if (currentDestination?.route != AppScreens.Login.route) { // Ocultar BottomBar en LoginScreen
        NavigationBar {
            screens.forEach { screen ->
                if (currentDestination != null) {
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: AppScreens,
    currentDestination: NavDestination,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.id)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screen.icon, contentDescription = "") },
        label = { Text(text = screen.title, fontSize = 10.sp) }
    )
}