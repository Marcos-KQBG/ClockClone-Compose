package com.marcos.clockclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.marcos.clockclone.ui.navigation.NavGraph
import com.marcos.clockclone.ui.screens.list.ListViewModel
import com.marcos.clockclone.ui.screens.map.MapViewModel
import com.marcos.clockclone.ui.screens.splash.SplashViewModel
import com.marcos.clockclone.ui.theme.ClockCloneTheme

class MainActivity : ComponentActivity() {

    // Inicializamos los ViewModels que vivirán durante toda la sesión de la App
    private val splashViewModel: SplashViewModel by viewModels()
    private val listViewModel: ListViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Aplicamos el tema oscuro/personalizado de tu App
            ClockCloneTheme {
                // Invocamos el NavGraph, que es el que decide qué pantalla mostrar
                // basándose en el estado de la navegación (Splash -> HomeContainer).
                NavGraph(
                    splashViewModel = splashViewModel,
                    listViewModel = listViewModel,
                    mapViewModel = mapViewModel
                )
            }
        }
    }
}