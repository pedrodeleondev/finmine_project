package com.dappsm.feat_auth.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dappsm.feat_auth.viewmodel.AuthState
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.ui_core.theme.Poppins

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: authviewmodel
) {
    val context= LocalContext.current
    val authState=authViewModel.authState.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate("profile")
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Fin Mine",
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "TU DINERO, TU CONTROL",
            fontFamily = Poppins,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "INICIA SESIÓN",
            fontFamily = Poppins,
            color = Color(0xFFDC652D),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Correo electrónico:",fontFamily = Poppins)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("ej. prueba@gmail.com",fontFamily = Poppins) },
            modifier = Modifier.fillMaxWidth(),

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Contraseña:")
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("ej. FhdW32*M",fontFamily = Poppins,) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "¿No tienes cuenta?",fontFamily = Poppins)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Regístrate aquí",
                fontFamily = Poppins,
                color = Color(0xFFDC652D),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                authViewModel.login(email, password)

            },
            enabled = authState.value!=AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3B3039)
            )
        ) {
            Text(text = "Iniciar Sesión",fontFamily = Poppins, color = Color.White)
        }
    }
}
