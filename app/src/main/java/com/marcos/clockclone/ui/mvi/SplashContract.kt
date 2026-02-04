package com.marcos.clockclone.ui.mvi

//informacion que la pantalla necesita
data class SplashState(
    val isFinished: Boolean = false
)

//Definimos que acciones pueden ocurrir
sealed class SplashIntent {
    object StartTimer: SplashIntent()
}