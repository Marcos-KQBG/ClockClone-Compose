package com.marcos.clockclone.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcos.clockclone.data.local.Alarm
import com.marcos.clockclone.data.local.AlarmDao
import com.marcos.clockclone.ui.mvi.ListIntent
import com.marcos.clockclone.ui.mvi.ListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(private val alarmDao: AlarmDao) : ViewModel() {

    // La fuente de verdad es Room
    // Cada vez que la DB cambie, la UI se actualizará automáticamente.
    val state: StateFlow<ListState> = alarmDao.getAllAlarms()
        .map { ListState(alarms = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ListState()
        )

    fun handleIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.DeleteAlarm -> {
                viewModelScope.launch {
                    val alarm = state.value.alarms.find { it.id == intent.id }
                    alarm?.let { alarmDao.deleteAlarm(it) }
                }
            }
            is ListIntent.AddAlarm -> {
                viewModelScope.launch {
                    // Insertamos en Room y él se encarga de avisar a la UI
                    alarmDao.insertAlarm(Alarm(time = "00:00", label = "Nueva Alarma", isActive = true, date = ""))
                }
            }
            is ListIntent.ToggleAlarm -> {
                viewModelScope.launch {
                    val alarm = state.value.alarms.find { it.id == intent.alarmId }
                    alarm?.let {
                        // Actualizamos el estado en la DB
                        alarmDao.insertAlarm(it.copy(isActive = !it.isActive))
                    }
                }
            }
            is ListIntent.LoadAlarms -> {
                // 30 ejemplos
                /*
                viewModelScope.launch {
                    if (state.value.alarms.isEmpty()) {
                        repeat(30) { i ->
                            alarmDao.insertAlarm(Alarm(time = "${8 + (i / 10)}:00", label = "Alarma $i", isActive = false, date = ""))
                        }
                    }
                }*/
            }
        }
    }
}
