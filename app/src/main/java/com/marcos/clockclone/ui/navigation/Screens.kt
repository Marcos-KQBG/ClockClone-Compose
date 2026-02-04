package com.marcos.clockclone.ui.navigation

sealed class Screens(val route: String) {
    object Splash : Screens("splash")
    object Main : Screens("main")
    object Detail : Screens("detail")
}