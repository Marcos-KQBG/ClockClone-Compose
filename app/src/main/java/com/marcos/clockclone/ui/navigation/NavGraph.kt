package com.marcos.clockclone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marcos.clockclone.ui.screens.detail.DetailScreen
import com.marcos.clockclone.ui.screens.detail.DetailViewModel
import com.marcos.clockclone.ui.screens.list.ListViewModel
import com.marcos.clockclone.ui.screens.list.MainScreen
import com.marcos.clockclone.ui.screens.map.MapScreen
import com.marcos.clockclone.ui.screens.splash.SplashScreen
import com.marcos.clockclone.ui.screens.splash.SplashViewModel

@Composable
fun NavGraph(
    splashViewModel: SplashViewModel,
    listViewModel: ListViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        // 1. SPLASH
        composable(route = Screens.Splash.route) {
            SplashScreen(
                viewModel = splashViewModel,
                onNavigateToMain = {
                    navController.navigate(Screens.Main.route) {
                        popUpTo(Screens.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. PRINCIPAL (ALARMAS)
        composable(route = Screens.Main.route) {
            MainScreen(
                viewModel = listViewModel,
                navController = navController
            )
        }

        // 3. DETALLE (EDICIÓN) - Aquí es donde faltaba el viewModel
        composable(
            route = Screens.Detail.route,
            arguments = listOf(navArgument("alarmId") { type = NavType.IntType })
        ) { backStackEntry ->
            val alarmId = backStackEntry.arguments?.getInt("alarmId")

            // Creamos el ViewModel específico para la edición
            val detailViewModel: DetailViewModel = viewModel()

            DetailScreen(
                alarmId = alarmId,
                viewModel = detailViewModel, // Ahora sí se lo pasamos
                onBack = { navController.popBackStack() }
            )
        }

        // 4. MAPA (RELOJ MUNDIAL)
        composable(
            route = "map_detail/{name}/{lat}/{lng}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("lat") { type = NavType.FloatType },
                navArgument("lng") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val lat = backStackEntry.arguments?.getFloat("lat")?.toDouble() ?: 0.0
            val lng = backStackEntry.arguments?.getFloat("lng")?.toDouble() ?: 0.0

            MapScreen(
                cityName = name,
                lat = lat,
                lng = lng,
                onBack = { navController.popBackStack() }
            )
        }
    }
}