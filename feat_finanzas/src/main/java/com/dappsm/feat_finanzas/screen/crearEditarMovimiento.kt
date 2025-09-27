package com.dappsm.feat_finanzas.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_finanzas.R
import com.dappsm.feat_finanzas.viewmodel.MovimientoUiViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.data_core.model.Movimiento

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarCMovements(onBackClick: () -> Unit, esEdicion: Boolean) {
    TopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (esEdicion) "Editar" else "Crear",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                Text(
                    text = "movimientos",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.billetos),
                    contentDescription = "Regresar",
                    modifier = Modifier.size(30.dp),
                )
            }
        }
    )
}

@Composable
fun formMovimiento(
    movimientoExistente: Movimiento? = null,
    onBackClick: () -> Unit,
    viewModel: MovimientoUiViewModel = viewModel()
) {
    var tipo by remember { mutableStateOf(movimientoExistente?.tipo ?: "") }
    var monto by remember { mutableStateOf(movimientoExistente?.cantidad ?: "") }
    var metodopago by remember { mutableStateOf(movimientoExistente?.metodoPago ?: "") }
    var motivo by remember { mutableStateOf(movimientoExistente?.motivo ?: "") }
    val esEdicion = movimientoExistente != null

    Scaffold(
        topBar = { topBarCMovements(esEdicion = esEdicion, onBackClick = onBackClick) },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        if (esEdicion) {
                            viewModel.actualizar(
                                movimientoExistente!!,
                                tipo,
                                monto,
                                metodopago,
                                motivo
                            )
                        } else {
                            viewModel.agregar(
                                usuarioId = 1,
                                tipo = tipo,
                                cantidadStr = monto,
                                metodoPago = metodopago,
                                motivo = motivo
                            )
                        }
                        onBackClick()
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        text = if (esEdicion) "Actualizar " else "Agregar ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tipo de movimiento:",
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
                value = tipo,
                onValueChange = { tipo = it },
                placeholder = { Text("ej. \"ingreso\"/\"egreso\"", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light) },
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Monto:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(10.dp),
                value = monto,
                onValueChange = { monto = it },
                placeholder = { Text("ej. \"120 pesos\"", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light) },
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Método de pago:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(10.dp),
                value = metodopago,
                onValueChange = { metodopago = it },
                placeholder = { Text("ej. \"Efectivo\"/\"Tarjeta\"", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light) },
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Motivo:",
                fontSize = 20.sp,
                maxLines = 3,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                shape = RoundedCornerShape(10.dp),
                value = motivo,
                onValueChange = { motivo = it },
                placeholder = { Text("ej.\"Pago de préstamo\"", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light) },
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
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
