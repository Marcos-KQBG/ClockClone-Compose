package com.marcos.clockclone.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
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
            .background(Color(0xFF121212)), // Fondo oscuro (AMOLED style)
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mostramos el icono que acabas de importar
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "Logo Reloj",
                tint = Color.Cyan, // Color llamativo para el reloj
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "ClockClone",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(48.dp)) // Espacio extra

            // La rueda de carga
            CircularProgressIndicator(
                color = Color.Cyan,
                strokeWidth = 3.dp,
                modifier = Modifier.size(40.dp)
            )
        }

        // Un detalle extra: un texto al pie de página
        Text(
            text = "By Marcos, Jorge y Héctor",
            color = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            fontSize = 12.sp
        )
    }
}