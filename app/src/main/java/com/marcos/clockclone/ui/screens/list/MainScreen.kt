package com.marcos.clockclone.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.marcos.clockclone.data.local.Alarm
import com.marcos.clockclone.ui.mvi.ListIntent

@Composable
fun MainScreen(
    viewModel: ListViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Accedemos al estado completo del ViewModel (MVI)
    val state by viewModel.state.collectAsState()
    val alarms = state.alarms

    // Cargamos las alarmas iniciales si la lista está vacía
    LaunchedEffect(Unit) {
        if (alarms.isEmpty()) {
            viewModel.handleIntent(ListIntent.LoadAlarms)
        }
    }

    // Usamos Box para superponer el FloatingActionButton sobre la lista
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Alarmas",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(alarms, key = { it.id }) { alarm ->
                    AlarmItem(
                        alarm = alarm,
                        onClick = {
                            // Navegación a la ruta de detalle con ID
                            navController.navigate("detail/${alarm.id}")
                        },
                        onToggle = {
                            viewModel.handleIntent(ListIntent.ToggleAlarm(alarm.id))
                        }
                    )
                }
            }
        }

        // El Botón Flotante (FAB) posicionado abajo a la derecha
        FloatingActionButton(
            onClick = {
                // Llamamos a la intención de añadir nueva alarma
                viewModel.handleIntent(ListIntent.AddAlarm)
            },
            containerColor = Color.Cyan,
            contentColor = Color.Black,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Agregar Alarma")
        }
    }
}

@Composable
fun AlarmItem(
    alarm: Alarm,
    onClick: () -> Unit,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = alarm.time,
                    fontSize = 40.sp,
                    color = if (alarm.isActive) Color.White else Color.Gray
                )
                Text(
                    text = alarm.label,
                    color = if (alarm.isActive) Color.White else Color.Gray
                )
            }
            Switch(
                checked = alarm.isActive,
                onCheckedChange = { onToggle() }
            )
        }
    }
}