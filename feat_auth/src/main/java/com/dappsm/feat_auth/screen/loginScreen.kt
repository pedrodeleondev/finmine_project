package com.dappsm.feat_auth.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dappsm.feat_auth.viewmodel.AuthState
import com.dappsm.feat_auth.viewmodel.authviewmodel
import com.dappsm.ui_core.theme.Poppins
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: authviewmodel
) {
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val credentialManager = remember {
        CredentialManager.create(context)
    }

    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId("682676641193-0vgc3hean7oani236eumbqqjfk8a4aln.apps.googleusercontent.com")
        .setFilterByAuthorizedAccounts(true)
        .build()

    val getCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    LaunchedEffect(authState.value) {
        when (val state = authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("main")
            }
            is AuthState.Error -> {
                Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fin Mine",
                fontFamily = Poppins,
                style = TextStyle(fontSize = 58.sp, color = MaterialTheme.colorScheme.onBackground),
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "TU DINERO, TU CONTROL",
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                style = TextStyle(fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "INICIA SESIÓN",
                fontFamily = Poppins,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Correo electrónico:",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        "ej. prueba@gmail.com",
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Contraseña:",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        "ej. FhdW32*M",
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            )

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "¿No tienes cuenta?",
                    fontFamily = Poppins,
                    style = TextStyle(fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Regístrate aquí",
                    fontFamily = Poppins,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = TextStyle(fontSize = 12.sp),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Correo y contraseña requeridos", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.login(email, password)
                    }
                },
                enabled = authState.value != AuthState.Loading,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(230.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Iniciar Sesión",
                    fontFamily = Poppins,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            val response: GetCredentialResponse =
                                credentialManager.getCredential(
                                    request = getCredentialRequest,
                                    context = context
                                )

                            try {
                                val googleIdTokenCredential =
                                    GoogleIdTokenCredential.createFrom(response.credential.data)

                                val idToken = googleIdTokenCredential.idToken
                                if (!idToken.isNullOrEmpty()) {
                                    authViewModel.firebaseAuthWithGoogle(idToken)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "No se pudo obtener el idToken de Google",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Credencial inválida: ${e.message ?: "desconocido"}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: GetCredentialException) {
                            Toast.makeText(
                                context,
                                "Error en Google Sign-In: ${e.errorMessage ?: e.message ?: "desconocido"}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.width(230.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2F5FED)
                )
            ) {
                Text("Iniciar sesión con Google", color = Color.White, fontFamily = Poppins)
            }
        }
    }
}
