package com.dappsm.nav_core.screenRoutes.navigation

sealed class Screens(val route: String) {
    // ====== Movimientos ======
    object Movimientos : Screens("movimientos")
    object FormMovimiento : Screens("formMovimiento")
    object DetalleMovimiento : Screens("detalleMovimiento")

    // ====== Mensuales ======
    object Mensuales : Screens("mensuales")

    // ====== Notas ======
    object Notas : Screens("notas")
    object FormNota : Screens("formNota")

    // ====== Perfil ======
    object Perfil : Screens("perfil")

    // ====== Configuración ======
    object Config : Screens("config")
}
