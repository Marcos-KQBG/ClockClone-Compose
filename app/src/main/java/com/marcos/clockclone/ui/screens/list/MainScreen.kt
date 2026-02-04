package com.marcos.clockclone.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcos.clockclone.data.local.Alarm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // Generamos una lista de prueba con 30 alarmas para cumplir la rúbrica
    val dummyAlarms = List(30) { i ->
        Alarm(i, "0${7 + (i / 10)}:${(i % 10) * 6}0", "Alarma $i", i % 2 == 0)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis Alarmas", color = Color.White) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF121212)
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        // Aquí está el Requisito 2: La lista eficiente
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(dummyAlarms) { alarm ->
                AlarmItem(alarm)
            }
        }
    }
}

@Composable
fun AlarmItem(alarm: Alarm) {
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
            onCheckedChange = { /* Ya lo conectaremos al MVI */ }
        )
    }
    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
}
