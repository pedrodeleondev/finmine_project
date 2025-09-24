package com.dappsm.nav_core

sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash")
    object Login : AppScreens("login")
    object Home : AppScreens("home")
}