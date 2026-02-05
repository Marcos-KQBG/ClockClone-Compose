package com.marcos.clockclone.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcos.clockclone.ui.mvi.DetailIntent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    alarmId: Int?,
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    // Escuchamos el estado del ViewModel
    val state by viewModel.state.collectAsState()

    // Cargamos la alarma nada más abrir la pantalla
    LaunchedEffect(alarmId) {
        alarmId?.let { viewModel.handleIntent(DetailIntent.LoadAlarm(it)) }
    }

    Scaffold(
        containerColor = Color(0xFF121212),
        topBar = {
            TopAppBar(
                title = { Text("Editar Alarma", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Muestra la hora (clicable en el futuro para abrir el reloj)
            Text(
                text = state.time,
                fontSize = 80.sp,
                color = Color.Cyan,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Campo para el NOMBRE
            OutlinedTextField(
                value = state.label,
                onValueChange = { viewModel.handleIntent(DetailIntent.UpdateName(it)) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Cyan,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Campo para la FECHA
            OutlinedTextField(
                value = state.date,
                onValueChange = { viewModel.handleIntent(DetailIntent.UpdateDate(it)) },
                label = { Text("Fecha (DD/MM/AAAA)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Cyan,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Cyan,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón GUARDAR
            Button(
                onClick = {
                    viewModel.handleIntent(DetailIntent.SaveAlarm)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text("GUARDAR", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}