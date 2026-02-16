package com.marcos.clockclone.ui.screens.detail

import androidx.compose.foundation.clickable
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
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    alarmId: Int?,
    viewModel: DetailViewModel,
    onBack: () -> Unit
) {
    // Escuchamos el estado del ViewModel
    val state by viewModel.state.collectAsState()

    // Estados para controlar la visibilidad de los diálogos
    var showTimePicker by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Cargamos la alarma nada más abrir la pantalla
    LaunchedEffect(alarmId) {
        alarmId?.let { viewModel.handleIntent(DetailIntent.LoadAlarm(it)) }
    }

    // --- LÓGICA DEL SELECTOR DE HORA ---
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = state.time.split(":")[0].toIntOrNull() ?: 8,
            initialMinute = state.time.split(":")[1].toIntOrNull() ?: 0
        )

        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val formattedTime = String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                    viewModel.handleIntent(DetailIntent.UpdateTime(formattedTime))
                    showTimePicker = false
                }) { Text("ACEPTAR", color = Color.Cyan) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("CANCELAR", color = Color.White) }
            },
            containerColor = Color(0xFF1C1C1E),
            text = { TimePicker(state = timePickerState) }
        )
    }

    // --- LÓGICA DEL SELECTOR DE FECHA ---
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val dateString = sdf.format(Date(millis))
                        viewModel.handleIntent(DetailIntent.UpdateDate(dateString))
                    }
                    showDatePicker = false
                }) { Text("ACEPTAR", color = Color.Cyan) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("CANCELAR", color = Color.White) }
            },
            colors = DatePickerDefaults.colors(containerColor = Color(0xFF1C1C1E))
        ) {
            DatePicker(state = datePickerState)
        }
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
            // Muestra la hora (Clicable para abrir el selector)
            Text(
                text = state.time,
                fontSize = 80.sp,
                color = Color.Cyan,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { showTimePicker = true }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Campo para el NOMBRE
            OutlinedTextField(
                value = state.label,
                onValueChange = { viewModel.handleIntent(DetailIntent.UpdateName(it)) },
                label = { Text("Nombre de la alarma") },
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

            // Campo para la FECHA (Clicable para abrir el calendario)
            OutlinedTextField(
                value = state.date,
                onValueChange = { },
                label = { Text("Fecha") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                enabled = false, // Deshabilitamos escritura manual
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    disabledTextColor = Color.White,
                    disabledContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón GUARDAR
            Button(
                onClick = {
                    viewModel.handleIntent(DetailIntent.SaveAlarm)
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text("GUARDAR", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}