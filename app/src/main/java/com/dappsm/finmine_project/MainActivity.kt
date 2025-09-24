package com.dappsm.finmine_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dappsm.feat_auth.screen.PruebaLogin
import com.dappsm.feat_config.isdarkmode.AppDatabase
import com.dappsm.feat_config.isdarkmode.SettingsRepository
import com.dappsm.feat_splash.screen.SplashDayScreen
import com.dappsm.finmine_project.ui.theme.Finmine_projectTheme
import com.dappsm.feat_splash.screen.SplashNightScreen
import com.dappsm.nav_core.AppScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Finmine_projectTheme {
                FuncionamientoApp()
            }

        }
    }
}
@Composable
fun FuncionamientoApp(){
    val navController = rememberNavController()
    val context = LocalContext.current

    val db = remember { AppDatabase.getInstance(context) }
    val repo = remember { SettingsRepository(db.settingsDao()) }

    var isDarkMode by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    //poner true o false pa probar las splash
    LaunchedEffect(Unit) {
        scope.launch {
            withContext(Dispatchers.IO) { repo.setDarkMode(true) }

            val saved = withContext(Dispatchers.IO) { repo.isDarkMode() }
            isDarkMode = saved
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppScreens.Splash.route
    ) {
        composable(AppScreens.Splash.route) {
            if (isDarkMode) {
                SplashNightScreen(navController)
            } else {
                SplashDayScreen(navController)
            }
        }
        composable(AppScreens.Login.route) {
            PruebaLogin(navController)
        }
        composable(AppScreens.Home.route) {
            // HomeScreen()
        }
    }
}
