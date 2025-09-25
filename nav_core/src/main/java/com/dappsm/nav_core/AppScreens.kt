package com.dappsm.nav_core

sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash")
    object authScreen : AppScreens("authscreen")
}