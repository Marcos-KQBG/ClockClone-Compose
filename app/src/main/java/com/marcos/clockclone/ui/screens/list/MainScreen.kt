package com.marcos.clockclone.ui.screens.list

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.marcos.clockclone.data.local.Alarm
import com.marcos.clockclone.ui.mvi.ListIntent
import com.marcos.clockclone.ui.navigation.Screens

@Composable
fun MainScreen(viewModel: ListViewModel, navController: NavHostController) {
    val state by viewModel.state.collectAsState()

    // Cargamos las alarmas al entrar por primera vez
    LaunchedEffect(Unit) {
        viewModel.handleIntent(ListIntent.LoadAlarms)
    }

    Scaffold(
        topBar = { /* ... tu TopBar de antes ... */ },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.handleIntent(ListIntent.AddAlarm) },
                containerColor = Color.Cyan
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.Black)
            }
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(state.alarms) { alarm ->
                AlarmItem(
                    alarm = alarm,
                    onToggle = { viewModel.handleIntent(ListIntent.ToggleAlarm(alarm.id)) },
                    onClick = {
                        // Usamos la función que creamos en Screens.kt para generar la ruta correcta
                        navController.navigate(Screens.Detail.createRoute(alarm.id))
                    }
                )
            }
        }
    }
}

@Composable
fun AlarmItem(
    alarm: Alarm,
    onToggle: () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = alarm.time, fontSize = 32.sp, color = Color.White)
            Text(text = alarm.label, fontSize = 14.sp, color = Color.Gray)
        }
        Switch(
            checked = alarm.isActive,
            // Ahora cuando el Switch cambie, ejecutamos la función que viene de arriba
            onCheckedChange = { onToggle() }
        )
    }
    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
}
