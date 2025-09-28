package com.dappsm.feat_finanzas.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.feat_finanzas.viewmodel.MovimientoUiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topbarIEM() {
    TopAppBar(
        title = {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = "Ingresos / Egresos",
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "mensuales",
                    style = TextStyle(
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    )
}

@Composable
fun IngresosEgresosMes(
    viewModel: MovimientoUiViewModel = viewModel()
) {
    val movimientos by viewModel.movimientos.collectAsState()
    var mes by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var mesError by remember { mutableStateOf(false) }
    var anioError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(movimientos) {
        isLoading = false
    }

    Scaffold(
        topBar = { topbarIEM() },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text("Mes:", style = TextStyle(color = MaterialTheme.colorScheme.primaryContainer, fontSize = 19.sp))
                    OutlinedTextField(
                        value = mes,
                        onValueChange = { input ->
                            val filtrado = input.filter { it.isDigit() }.take(2)
                            mes = filtrado
                            mesError = filtrado.isNotEmpty() && (filtrado.toInt() !in 1..12)
                        },
                        modifier = Modifier.width(145.dp),
                        placeholder = { Text("ej.09", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light) },
                        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                        shape = RoundedCornerShape(10.dp),
                        isError = mesError,
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
                    if (mesError) {
                        Text("El mes debe estar entre 01 y 12", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Año:", style = TextStyle(color = MaterialTheme.colorScheme.primaryContainer, fontSize = 19.sp))
                    OutlinedTextField(
                        value = anio,
                        onValueChange = { input ->
                            val filtrado = input.filter { it.isDigit() }.take(4)
                            anio = filtrado
                            anioError = filtrado.length == 4 && filtrado.toInt() !in 1900..2100
                        },
                        modifier = Modifier.width(200.dp),
                        placeholder = { Text("ej.2025", color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light) },
                        keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                        shape = RoundedCornerShape(10.dp),
                        isError = anioError,
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
                    if (anioError) {
                        Text("Introduce un año válido (1900-2100)", color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            }

            val movimientosFiltrados = movimientos.filter { movimiento ->
                (mes.isEmpty() || movimiento.mes.toString().padStart(2, '0') == mes) &&
                        (anio.isEmpty() || movimiento.fecha.year.toString() == anio)
            }

            Spacer(modifier = Modifier.height(20.dp))

            when {
                isLoading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                }
                movimientosFiltrados.isEmpty() -> {
                    Text(
                        text = "No hay movimientos para este período",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.padding(20.dp)
                    )
                }
                else -> {
                    LazyColumn {
                        movimientosFiltrados.groupBy { "${movimientoMes(it.mes)} ${it.fecha.year}" }.forEach { (mesAnio, listaMovimientos) ->
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = mesAnio,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            color = MaterialTheme.colorScheme.background,
                                            fontSize = 18.sp,
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .fillMaxWidth()
                                        )
                                        listaMovimientos.forEach { movimiento ->
                                            val textMovimiento = buildAnnotatedString {
                                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Medium)) {
                                                    append("${movimiento.tipo} de:")
                                                }
                                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light)) {
                                                    append(" ${movimiento.cantidad}")
                                                }
                                            }
                                            Text(text = textMovimiento, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun movimientoMes(mes: Int): String {
    return listOf(
        "Enero","Febrero","Marzo","Abril","Mayo","Junio",
        "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
    )[mes - 1]
}
