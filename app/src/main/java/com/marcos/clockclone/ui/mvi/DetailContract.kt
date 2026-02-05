package com.marcos.clockclone.ui.mvi

data class DetailState(
    val alarmId: Int? = null,
    val time: String = "00:00",
    val label: String = ""
)

sealed class DetailIntent {
    data class LoadAlarm(val id: Int) : DetailIntent()
    data class UpdateTime(val newTime: String) : DetailIntent()
    object SaveAlarm : DetailIntent()
}