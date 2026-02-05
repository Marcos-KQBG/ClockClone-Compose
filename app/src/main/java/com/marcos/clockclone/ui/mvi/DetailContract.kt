package com.marcos.clockclone.ui.mvi

// Definimos la "foto" de la pantalla de detalle
data class DetailState(
    val alarmId: Int? = null,
    val time: String = "08:00",
    val label: String = "",
    val date: String = "05/02/2026", // Fecha por defecto
    val isLoading: Boolean = false
)

// Definimos qué acciones puede hacer el usuario
sealed class DetailIntent {
    data class LoadAlarm(val id: Int) : DetailIntent()
    data class UpdateName(val newName: String) : DetailIntent()
    data class UpdateTime(val newTime: String) : DetailIntent()
    data class UpdateDate(val newDate: String) : DetailIntent()
    object SaveAlarm : DetailIntent()
}