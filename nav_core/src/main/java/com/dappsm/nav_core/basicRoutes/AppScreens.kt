package com.dappsm.nav_core.basicRoutes

sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash")
    object authScreen : AppScreens("authscreen")
    object MainApp : AppScreens("mainapp")
}