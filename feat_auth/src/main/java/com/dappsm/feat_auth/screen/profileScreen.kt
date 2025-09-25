package com.dappsm.feat_auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dappsm.feat_auth.viewmodel.AuthState
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController,authviewmodel: authviewmodel){
    val authState=authviewmodel.authState.observeAsState()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email ?: "Correo no disponible"
    LaunchedEffect(authState.value) {
        when(authState.value){

            is AuthState.Unauthenticated->{
                navController.navigate("login")
            }
            else -> Unit
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = email,
            fontSize = 32.sp
        )

        TextButton(
            onClick = {
                authviewmodel.signout()
            }
        ) {
            Text(text = "Sign out")
        }
    }

}