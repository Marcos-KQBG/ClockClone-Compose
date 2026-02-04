package com.marcos.clockclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marcos.clockclone.ui.screens.splash.SplashScreen
import com.marcos.clockclone.ui.screens.splash.SplashViewModel

@Composable
fun NavGraph(splashViewModel: SplashViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route // Empezamos siempre en el Splash
    ) {
        // Definimos la ruta del Splash
        composable(route = Screens.Splash.route) {
            SplashScreen(
                viewModel = splashViewModel,
                onNavigateToMain = {
                    // Cuando el Splash diga que terminó, vamos al Main
                    navController.navigate(Screens.Main.route) {
                        // Borramos el Splash del historial para que el usuario no vuelva atrás
                        popUpTo(Screens.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Definimos la ruta de la pantalla principal (de momento vacía)
        composable(route = Screens.Main.route) {
            // Aquí la pantalla de Alarmas/Reloj más adelante
        }
    }
}