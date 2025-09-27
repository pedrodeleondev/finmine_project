package com.dappsm.feat_finanzas.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.data_core.model.Movimiento
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarDetails(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Detalles de",
                    textAlign = TextAlign.Right,
                    style = TextStyle(
                        fontSize = 31.sp,
                        color = MaterialTheme.colorScheme.background,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "movimiento",
                    textAlign = TextAlign.Right,
                    style = TextStyle(
                        fontSize = 31.sp,
                        color = MaterialTheme.colorScheme.background,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        windowInsets = WindowInsets(0, 0, 0, 0)
    )
}

@Composable
fun DetallesMovimiento(
    movimiento: Movimiento,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val fechaFormateada = movimiento.fecha.format(formatter)

    Scaffold(contentWindowInsets = WindowInsets(0.dp)) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                topBarDetails(onBackClick)
                Spacer(modifier = Modifier.height(80.dp))
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = movimiento.tipo.uppercase(),
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 15.sp,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val textMonto = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.SemiBold)) { append("Monto:") }
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light)) { append(" ${movimiento.cantidad}") }
                    }
                    Text(
                        text = textMonto,
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                val textFecha = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.SemiBold)) { append("Fecha:") }
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" $fechaFormateada") }
                }
                Text(
                    text = textFecha,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
                val textMetodo = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.SemiBold)) { append("Método de pago:") }
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${movimiento.metodoPago}") }
                }
                Text(
                    text = textMetodo,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
                val textMotivo = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.SemiBold)) { append("Motivo:") }
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${movimiento.motivo ?: ""}") }
                }
                Text(
                    text = textMotivo,
                    fontSize = 24.sp,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.width(300.dp).height(90.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { onEditClick() },
                        modifier = Modifier
                            .width(300.dp)
                            .height(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Editar movimiento",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
