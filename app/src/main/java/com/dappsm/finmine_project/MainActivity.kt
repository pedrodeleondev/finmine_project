package com.dappsm.finmine_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.finmine_project.ui.theme.Finmine_projectTheme
import com.dappsm.feat_splash.screen.SplashDayScreen
import com.dappsm.nav_core.root.RootNavigation
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Finmine_projectTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var showSplash by remember { mutableStateOf(true) }
                    val vm: authviewmodel = viewModel()
                    AppDatabaseInstance.init(this)

                    LaunchedEffect(Unit) {
                        delay(1500)
                        showSplash = false
                    }

                    if (showSplash) {
                        SplashDayScreen()
                    } else {
                        RootNavigation(vm)
                    }
                }
            }
        }
    }
}

