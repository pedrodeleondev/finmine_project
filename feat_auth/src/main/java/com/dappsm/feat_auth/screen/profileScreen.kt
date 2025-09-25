package com.dappsm.feat_auth.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dappsm.feat_auth.viewmodel.AuthState
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.ui_core.theme.Poppins
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController, authviewmodel: authviewmodel) {
    val authState = authviewmodel.authState.observeAsState()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email ?: "Correo no disponible"
    LaunchedEffect(authState.value) {
        when (authState.value) {

            is AuthState.Unauthenticated -> {
                navController.navigate("login")
            }

            else -> Unit
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text(
                text = "Mi perfil",
                fontSize = 29.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, start = 15.dp)
            )

        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = email,
                fontSize = 25.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { authviewmodel.signout() },
                modifier = Modifier
                    .width(356.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10),
                border = BorderStroke(2.dp, Color(0xFFDC652D)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFDC652D)
                )
            ) {
                Text(
                    text = "Cerrar sesión",
                    fontSize = 18.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}