package com.dappsm.feat_auth.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.ui_core.theme.Poppins
import com.google.firebase.auth.FirebaseAuth
import com.dappsm.feat_auth.R
import androidx.compose.material3.MaterialTheme

@Composable
fun ProfileImage() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val photoUrl = currentUser?.photoUrl

    Image(
        painter = if (photoUrl != null) {
            rememberAsyncImagePainter(photoUrl)
        } else {
            painterResource(R.drawable.profile_picture)
        },
        contentDescription = "Profile picture",
        modifier = Modifier
            .size(222.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileScreen(
    authViewModel: authviewmodel,
    onLogout: () -> Unit
) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email ?: "Correo no disponible"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(
                text = "Mi perfil",
                fontSize = 29.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 20.dp, start = 15.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            ProfileImage()
            Text(
                text = email,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground
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
                onClick = { onLogout() },
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
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDC652D)
                )
            }
        }
    }
}
