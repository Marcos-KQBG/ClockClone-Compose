package com.marcos.clockclone.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcos.clockclone.data.local.Alarm
import com.marcos.clockclone.ui.mvi.ListIntent
import com.marcos.clockclone.ui.mvi.ListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state: StateFlow<ListState> = _state

    fun handleIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.LoadAlarms -> loadInitialAlarms()
            is ListIntent.ToggleAlarm -> toggleAlarmStatus(intent.alarmId)
            is ListIntent.AddAlarm -> addNewAlarm()
        }
    }

    private fun loadInitialAlarms() {
        // Simulamos carga de datos iniciales (los 30 que pide la rúbrica)
        val initialList = List(30) { i ->
            Alarm(i, "${8 + (i / 10)}:00", "Alarma $i", false)
        }
        _state.value = _state.value.copy(alarms = initialList)
    }

    private fun toggleAlarmStatus(id: Int) {
        // Buscamos la alarma y le damos la vuelta al interruptor
        val updatedList = _state.value.alarms.map { alarm ->
            if (alarm.id == id) alarm.copy(isActive = !alarm.isActive) else alarm
        }
        _state.value = _state.value.copy(alarms = updatedList)
    }

    private fun addNewAlarm() {
        val currentList = _state.value.alarms.toMutableList()
        val newId = if (currentList.isEmpty()) 0 else currentList.maxOf { it.id } + 1
        currentList.add(0, Alarm(newId, "00:00", "Nueva Alarma", true))
        _state.value = _state.value.copy(alarms = currentList)
    }
}
