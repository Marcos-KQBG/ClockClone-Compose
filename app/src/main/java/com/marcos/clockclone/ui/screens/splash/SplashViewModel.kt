package com.marcos.clockclone.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcos.clockclone.ui.mvi.SplashIntent
import com.marcos.clockclone.ui.mvi.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    fun handleIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.StartTimer -> {
                viewModelScope.launch {
                    // Esperamos 3 segundos como pide el requisito
                    delay(3000)
                    // Cambiamos el estado para decir que hemos terminado
                    _state.value = SplashState(isFinished = true)
                }
            }
        }
    }
}