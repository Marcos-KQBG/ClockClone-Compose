package com.marcos.clockclone.ui.mvi

import com.marcos.clockclone.data.local.Alarm

data class ListState(
    val alarms: List<Alarm> = emptyList(),
    val isLoading: Boolean = false
)

sealed class ListIntent {
    object LoadAlarms : ListIntent()
    data class ToggleAlarm(val alarmId: Int) : ListIntent()
    data class DeleteAlarm(val id: Int) : ListIntent()
    object AddAlarm : ListIntent()

}