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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dappsm.feat_auth.screen.PruebaLogin
import com.dappsm.feat_splash.screen.SplashDayScreen
import com.dappsm.finmine_project.ui.theme.Finmine_projectTheme
import com.dappsm.nav_core.AppScreens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Finmine_projectTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppScreens.Splash.route
                ) {
                    composable(AppScreens.Splash.route) {
                        SplashDayScreen(navController)
                    }
                    composable(AppScreens.Login.route) {
                        PruebaLogin(navController)
                    }
                    composable(AppScreens.Home.route) {
                        //HomeScreen()
                    }
                }
            }
        }
    }
}
