package com.marcos.clockclone.ui.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Main : Screens("main")
    // Usamos corchetes para indicar que pasaremos un ID
    object Detail : Screens("detail/{alarmId}") {
        fun createRoute(alarmId: Int) = "detail/$alarmId"
    }
}