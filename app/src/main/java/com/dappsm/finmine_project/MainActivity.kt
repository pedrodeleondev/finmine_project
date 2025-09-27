package com.dappsm.finmine_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.data_core.database.AppDatabaseInstance
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.finmine_project.ui.theme.Finmine_projectTheme
import com.dappsm.nav_core.root.RootNavigation
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Finmine_projectTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val vm: authviewmodel = viewModel()
                    AppDatabaseInstance.init(this)
                    RootNavigation(vm)


                    }
                }
            }
        }
    }

