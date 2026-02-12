package com.marcos.clockclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marcos.clockclone.ui.screens.HomeContainer
import com.marcos.clockclone.ui.screens.detail.DetailScreen
import com.marcos.clockclone.ui.screens.detail.DetailViewModel
import com.marcos.clockclone.ui.screens.list.ListViewModel
import com.marcos.clockclone.ui.screens.map.MapScreen
import com.marcos.clockclone.ui.screens.map.MapViewModel
import com.marcos.clockclone.ui.screens.splash.SplashScreen
import com.marcos.clockclone.ui.screens.splash.SplashViewModel

@Composable
fun NavGraph(
    splashViewModel: SplashViewModel,
    listViewModel: ListViewModel,
    mapViewModel: MapViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // 1. Pantalla de carga (Splash)
        composable(route = "splash") {
            SplashScreen(
                viewModel = splashViewModel,
                onNavigateToMain = {
                    navController.navigate("main") {
                        // Evitamos que el usuario pueda volver al Splash al pulsar atrás
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // 2. Contenedor Principal (Pestañas de Alarmas y Reloj Mundial)
        composable(route = "main") {
            HomeContainer(
                listViewModel = listViewModel,
                mapViewModel = mapViewModel, // Pasamos el MapViewModel
                navController = navController
            )
        }

        // 3. Pantalla de Detalle (Edición de una Alarma específica)
        composable(
            route = "detail/{alarmId}",
            arguments = listOf(navArgument("alarmId") { type = NavType.IntType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getInt("alarmId")

            // Inyectamos el DetailViewModel para manejar la lógica de edición
            val detailViewModel: DetailViewModel = viewModel()

            DetailScreen(
                alarmId = alarmId,
                viewModel = detailViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // 4. Pantalla de Mapa (Detalle de Ciudad del Reloj Mundial)
        composable(
            route = "map_detail/{name}/{lat}/{lng}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lng") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            // Los argumentos de navegación pasan como Float, los convertimos a Double para el mapa
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStackEntry.arguments?.getFloat("lng")?.toDouble() ?: 0.0

            MapScreen(
                cityName = name,
                lat = lat,
                lng = lng,
                mapViewModel = mapViewModel, // Pasamos el MapViewModel
                onBack = { navController.popBackStack() }
            )
        }
    }
}