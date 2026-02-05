package com.marcos.clockclone.data.local

data class WorldCity(
    val id: Int,
    val name: String,
    val timeDiff: String, // Ejemplo: "1 hora menos" o "Zona horaria local"
    val time: String,      // Ejemplo: "15:50"
    val temp: String,      // Ejemplo: "8°" (opcional, para el icono del tiempo)
    val latitude: Double,
    val longitude: Double
)