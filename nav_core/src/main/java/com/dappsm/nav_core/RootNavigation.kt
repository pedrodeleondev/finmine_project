package com.dappsm.nav_core.root

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dappsm.feat_auth.screen.LoginScreen
import com.dappsm.feat_auth.screen.ProfileScreen
import com.dappsm.feat_auth.screen.RegisterScreen
import com.dappsm.feat_auth.viewmodel.AuthState
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.feat_finanzas.screen.DetallesMovimiento
import com.dappsm.feat_finanzas.screen.MisMovimientosCard
import com.dappsm.feat_finanzas.screen.formMovimiento
import com.dappsm.feat_finanzas.viewmodel.MovimientoUiViewModel
import com.dappsm.feat_notas.screen.ListaNotas
import com.dappsm.feat_notas.screen.formNuevaNota
import com.dappsm.feat_notas.viewmodel.NotaUiViewModel
import com.dappsm.feat_splash.screen.SplashDayScreen
import com.dappsm.nav_core.screenRoutes.navigation.Screens
import com.dappsm.nav_core.screenRoutes.navigation.listOfNavItems
import kotlinx.coroutines.delay

@Composable
fun RootNavigation(authViewModel: authviewmodel) {
    val navController = rememberNavController()
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                    launchSingleTop = true
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                    launchSingleTop = true
                }
            }
            else -> Unit
        }
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashDayScreen()
            LaunchedEffect(Unit) { delay(1500) }
        }
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("register") {
            RegisterScreen(navController, authViewModel)
        }
        composable("main") {
            MainScaffold(rootNavController = navController, authViewModel = authViewModel)
        }
    }
}

@Composable
fun MainScaffold(rootNavController: NavHostController, authViewModel: authviewmodel) {
    val innerNavController = rememberNavController()

    Scaffold(bottomBar = { BottomNavBar(navController = innerNavController) }) { innerPadding: PaddingValues ->
        NavHost(
            navController = innerNavController,
            startDestination = Screens.Movimientos.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Movimientos.route) {
                val vm: MovimientoUiViewModel = viewModel()
                MisMovimientosCard(
                    viewModel = vm,
                    onAddClick = { innerNavController.navigate(Screens.FormMovimiento.route) },
                    onEditClick = { movimiento ->
                        innerNavController.navigate("${Screens.FormMovimiento.route}/${movimiento.id}")
                    },
                    onDetailsClick = { movimiento ->
                        innerNavController.navigate("${Screens.DetalleMovimiento.route}/${movimiento.id}")
                    }
                )
            }

            composable(Screens.FormMovimiento.route) {
                val vm: MovimientoUiViewModel = viewModel()
                formMovimiento(
                    movimientoExistente = null,
                    onBackClick = { innerNavController.popBackStack() },
                    viewModel = vm
                )
            }

            composable("${Screens.FormMovimiento.route}/{id}") { backStackEntry ->
                val vm: MovimientoUiViewModel = viewModel()
                val id = backStackEntry.arguments?.getString("id")
                val movimientos by vm.movimientos.collectAsState()
                val movimiento = movimientos.firstOrNull { it.id == id }
                formMovimiento(
                    movimientoExistente = movimiento,
                    onBackClick = { innerNavController.popBackStack() },
                    viewModel = vm
                )
            }

            composable("${Screens.DetalleMovimiento.route}/{id}") { backStackEntry ->
                val vm: MovimientoUiViewModel = viewModel()
                val id = backStackEntry.arguments?.getString("id")
                val movimientos by vm.movimientos.collectAsState()
                val movimiento = movimientos.firstOrNull { it.id == id }
                if (movimiento != null) {
                    DetallesMovimiento(
                        movimiento = movimiento,
                        onBackClick = { innerNavController.popBackStack() },
                        onEditClick = {
                            innerNavController.navigate("${Screens.FormMovimiento.route}/${movimiento.id}")
                        }
                    )
                }
            }

            composable(Screens.Notas.route) {
                val vm: NotaUiViewModel = viewModel()
                ListaNotas(
                    viewModel = vm,
                    onAddClick = { innerNavController.navigate(Screens.FormNota.route) }
                )
            }
            composable(Screens.FormNota.route) {
                val vm: NotaUiViewModel = viewModel()
                formNuevaNota(viewModel = vm, onBackClick = { innerNavController.popBackStack() })
            }

            composable(Screens.Perfil.route) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onLogout = { authViewModel.signout() }
                )
            }

            composable(Screens.Mensuales.route) { }
            composable(Screens.Config.route) { }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        listOfNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(Screens.Movimientos.route)
                    }
                },
                icon = {
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = MaterialTheme.colorScheme.background
                    )
                },
                label = null,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.background,
                    unselectedIconColor = MaterialTheme.colorScheme.background,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
