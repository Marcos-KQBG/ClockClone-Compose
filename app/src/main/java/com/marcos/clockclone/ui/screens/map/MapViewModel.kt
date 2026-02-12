package com.marcos.clockclone.ui.screens.map

import androidx.lifecycle.ViewModel
import com.marcos.clockclone.data.local.WorldCity

class MapViewModel : ViewModel() {
    val cities = listOf(
        WorldCity(1, "Londres", "1 hora menos", "15:51", "8°", 51.5074, -0.1278),
        WorldCity(2, "Madrid", "Zona horaria local", "16:51", "12°", 40.4168, -3.7038),
        WorldCity(3, "Nueva York", "6 horas menos", "10:51", "5°", 40.7128, -74.0060),
        WorldCity(4, "Tokio", "7 horas más", "23:51", "15°", 35.6895, 139.6917),
        WorldCity(5, "Sídney", "9 horas más", "01:51", "22°", -33.8688, 151.2093),
        WorldCity(6, "París", "Misma hora", "16:51", "10°", 48.8566, 2.3522),
        WorldCity(7, "Pekín", "6 horas más", "22:51", "18°", 39.9042, 121.4737)
    )
}