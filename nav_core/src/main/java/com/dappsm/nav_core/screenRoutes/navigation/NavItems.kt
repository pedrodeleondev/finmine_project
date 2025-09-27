package com.dappsm.nav_core.screenRoutes.navigation

import com.dappsm.nav_core.R

data class NavItem(
    val label: String,
    val icon: Int,
    val route: String,
)

val listOfNavItems: List<NavItem> = listOf(
    NavItem(
        label = "Movimientos",
        icon = R.drawable.dinero,
        route = Screens.movimientosScreen.name
    ),
    NavItem(
        label = "Mensuales",
        icon = R.drawable.mensuales,
        route = Screens.mensualesScreen.name
    ),
    NavItem(
        label = "Notas",
        icon = R.drawable.notas,
        route = Screens.notasScreen.name
    ),
    NavItem(
        label = "Perfil",
        icon = R.drawable.perfil,
        route = Screens.perfilScreen.name
    ),
    NavItem(
        label = "Config",
        icon = R.drawable.config,
        route = Screens.configScreen.name
    )
)
