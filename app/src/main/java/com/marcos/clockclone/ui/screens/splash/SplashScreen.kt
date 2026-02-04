package com.marcos.clockclone.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.marcos.clockclone.R
import com.marcos.clockclone.ui.mvi.SplashIntent

@Composable
fun SplashScreen(viewModel: SplashViewModel, onNavigateToMain: () -> Unit) {
    // Observamos el estado del ViewModel (MVI)
    val state by viewModel.state.collectAsState()

    // Este bloque se ejecuta una sola vez al cargar la pantalla
    LaunchedEffect(Unit) {
        viewModel.handleIntent(SplashIntent.StartTimer)
    }

    // Reaccionamos cuando el estado cambie a isFinished = true
    if (state.isFinished) {
        onNavigateToMain()
    }

    // Diseño visual con Jetpack Compose
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)), // Un fondo oscuro elegante
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Aquí pondremos un texto temporal hasta que tengamos un logo
            Text(
                text = "RELOJ",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 8.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cargando tu tiempo...",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}