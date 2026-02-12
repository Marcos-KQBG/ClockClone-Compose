package com.marcos.clockclone.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.marcos.clockclone.ui.screens.list.MainScreen
import com.marcos.clockclone.ui.screens.list.ListViewModel
import com.marcos.clockclone.ui.screens.map.MapViewModel
import com.marcos.clockclone.ui.screens.map.WorldClockScreen

@Composable
fun HomeContainer(
    listViewModel: ListViewModel,
    mapViewModel: MapViewModel, // Añadido el MapViewModel
    navController: androidx.navigation.NavHostController
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) } // <--- CAMBIO AQUÍ

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Alarma", color = Color.White) },
                    icon = { Icon(Icons.Default.Notifications, "Alarma", tint = Color.White) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Reloj mundial", color = Color.White) },
                    icon = { Icon(Icons.Default.Place, "Reloj mundial", tint = Color.White) }
                )
            }
        },
        containerColor = Color.Black
    ) { padding ->
        when (selectedTab) {
            // Pasamos el padding del Scaffold a través del Modifier para evitar que el contenido
            // quede oculto detrás de la barra de navegación inferior.
            0 -> MainScreen(
                viewModel = listViewModel,
                navController = navController,
                modifier = Modifier.padding(padding)
            )
            1 -> WorldClockScreen(
                navController = navController,
                mapViewModel = mapViewModel, // Pasamos el MapViewModel
                modifier = Modifier.padding(padding)
            )
        }
    }
}