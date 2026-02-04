package com.marcos.clockclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.marcos.clockclone.ui.navigation.NavGraph
import com.marcos.clockclone.ui.screens.splash.SplashViewModel
import com.marcos.clockclone.ui.theme.ClockCloneTheme

class MainActivity : ComponentActivity() {

    // Inicializamos el ViewModel del Splash aquí para pasárselo al NavGraph
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usamos el tema de nuestra app (colores, tipografía)
            ClockCloneTheme {
                // Llamamos al NavGraph
                NavGraph(splashViewModel = splashViewModel)
            }
        }
    }
}