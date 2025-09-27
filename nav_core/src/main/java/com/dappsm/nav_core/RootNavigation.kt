package com.dappsm.nav_core.root

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dappsm.data_core.model.Movimiento
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
            LaunchedEffect(Unit) {
                delay(1500)
            }
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
            startDestination = Screens.movimientosScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.movimientosScreen.name) {
                val vm: MovimientoUiViewModel = viewModel()
                MisMovimientosCard(
                    viewModel = vm,
                    onAddClick = { innerNavController.navigate(Screens.formMovimientoScreen.name) },
                    onEditClick = { movimiento ->
                        innerNavController.currentBackStackEntry?.savedStateHandle?.set("movimiento", movimiento)
                        innerNavController.navigate(Screens.formMovimientoScreen.name)
                    },
                    onDetailsClick = { movimiento ->
                        innerNavController.currentBackStackEntry?.savedStateHandle?.set("movimiento", movimiento)
                        innerNavController.navigate(Screens.detalleMovimientoScreen.name)
                    }
                )
            }
            composable(Screens.formMovimientoScreen.name) {
                val vm: MovimientoUiViewModel = viewModel()
                val movimiento = innerNavController.previousBackStackEntry?.savedStateHandle?.get<Movimiento>("movimiento")
                formMovimiento(
                    movimientoExistente = movimiento,
                    onBackClick = { innerNavController.popBackStack() },
                    viewModel = vm
                )
            }
            composable(Screens.detalleMovimientoScreen.name) {
                val movimiento = innerNavController.previousBackStackEntry?.savedStateHandle?.get<Movimiento>("movimiento")
                if (movimiento != null) {
                    DetallesMovimiento(
                        movimiento = movimiento,
                        onBackClick = { innerNavController.popBackStack() },
                        onEditClick = {
                            innerNavController.currentBackStackEntry?.savedStateHandle?.set("movimiento", movimiento)
                            innerNavController.navigate(Screens.formMovimientoScreen.name)
                        }
                    )
                }
            }
            composable(Screens.notasScreen.name) {
                val vm: NotaUiViewModel = viewModel()
                ListaNotas(
                    viewModel = vm,
                    onAddClick = { innerNavController.navigate(Screens.formNotaScreen.name) }
                )
            }
            composable(Screens.formNotaScreen.name) {
                val vm: NotaUiViewModel = viewModel()
                formNuevaNota(viewModel = vm, onBackClick = { innerNavController.popBackStack() })
            }
            composable(Screens.perfilScreen.name) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onLogout = { authViewModel.signout() }
                )
            }
            composable(Screens.mensualesScreen.name) { }
            composable(Screens.configScreen.name) { }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        listOfNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        popUpTo(Screens.movimientosScreen.name)
                    }
                },
                icon = {
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = item.icon),
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
