package com.marcos.clockclone.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.marcos.clockclone.ui.mvi.DetailIntent
import com.marcos.clockclone.ui.mvi.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {

    // El estado privado que solo el ViewModel puede cambiar
    private val _state = MutableStateFlow(DetailState())
    // El estado público que la pantalla lee
    val state: StateFlow<DetailState> = _state

    fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadAlarm -> {
                // Aquí simulamos que cargamos la alarma (luego vendrá de la base de datos)
                _state.value = _state.value.copy(
                    alarmId = intent.id,
                    label = "Alarma ${intent.id}"
                )
            }
            is DetailIntent.UpdateName -> {
                // Actualizamos el nombre en la "foto" del estado
                _state.value = _state.value.copy(label = intent.newName)
            }
            is DetailIntent.UpdateDate -> {
                // Actualizamos la fecha
                _state.value = _state.value.copy(date = intent.newDate)
            }
            is DetailIntent.UpdateTime -> {
                _state.value = _state.value.copy(time = intent.newTime)
            }
            is DetailIntent.SaveAlarm -> {
                // Por ahora solo imprimimos, aquí irá el guardado real
                println("Guardando cambios de alarma: ${_state.value.label}")
            }
        }
    }
}