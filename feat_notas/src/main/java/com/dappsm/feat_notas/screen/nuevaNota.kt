package com.dappsm.feat_notas.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.feat_notas.viewmodel.NotaUiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarNuevaNota(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Nueva Nota",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 8.dp, end = 3.dp),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    )
}

@Composable
fun formNuevaNota(
    onBackClick: () -> Unit,
    viewModel: NotaUiViewModel = viewModel()
) {
    var comentario by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        topBarNuevaNota(onBackClick)
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Comentario:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(10.dp),
            maxLines = 3,
            value = comentario,
            onValueChange = { comentario = it },
            placeholder = {
                Text(
                    "ej. \"Pagar mensualidad\"",
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.Light,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                cursorColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.height(70.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    if (comentario.isNotBlank()) {
                        viewModel.agregar(usuarioId = 1, contenido = comentario)
                        onBackClick()
                    } else {
                        Toast.makeText(context, "El comentario no puede estar vacío", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(74.dp)
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    text = "Agregar nota",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
