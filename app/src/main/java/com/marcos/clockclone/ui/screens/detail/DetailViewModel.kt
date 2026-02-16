package com.marcos.clockclone.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcos.clockclone.data.local.Alarm
import com.marcos.clockclone.data.local.AlarmDao
import com.marcos.clockclone.ui.mvi.DetailIntent
import com.marcos.clockclone.ui.mvi.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val alarmDao: AlarmDao) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadAlarm -> {
                viewModelScope.launch {
                    // Cargamos los datos reales de la base de datos
                    val alarm = alarmDao.getAlarmById(intent.id)
                    alarm?.let {
                        _state.value = _state.value.copy(
                            alarmId = it.id,
                            label = it.label,
                            time = it.time,
                            date = it.date
                        )
                    }
                }
            }
            is DetailIntent.UpdateName -> {
                _state.value = _state.value.copy(label = intent.newName)
            }
            is DetailIntent.UpdateDate -> {
                _state.value = _state.value.copy(date = intent.newDate)
            }
            is DetailIntent.UpdateTime -> {
                _state.value = _state.value.copy(time = intent.newTime)
            }
            is DetailIntent.SaveAlarm -> {
                viewModelScope.launch {
                    val current = _state.value
                    // Guardamos (Insert o Update) en la base de datos
                    alarmDao.insertAlarm(
                        Alarm(
                            id = current.alarmId ?: 0,
                            time = current.time,
                            label = current.label,
                            date = current.date,
                            isActive = true
                        )
                    )
                }
            }
        }
    }
}