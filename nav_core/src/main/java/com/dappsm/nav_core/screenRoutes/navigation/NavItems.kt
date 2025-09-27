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
        route = Screens.Movimientos.route
    ),
    NavItem(
        label = "Mensuales",
        icon = R.drawable.mensuales,
        route = Screens.Mensuales.route
    ),
    NavItem(
        label = "Notas",
        icon = R.drawable.notas,
        route = Screens.Notas.route
    ),
    NavItem(
        label = "Perfil",
        icon = R.drawable.perfil,
        route = Screens.Perfil.route
    ),
    NavItem(
        label = "Config",
        icon = R.drawable.config,
        route = Screens.Config.route
    )
)
