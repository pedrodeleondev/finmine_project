package com.dappsm.feat_finanzas.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.data_core.model.Movimiento
import com.dappsm.feat_finanzas.R
import com.dappsm.feat_finanzas.viewmodel.MovimientoUiViewModel
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisMovimientosCard(
    viewModel: MovimientoUiViewModel = viewModel(),
    onAddClick: () -> Unit = {},
    onEditClick: (Movimiento) -> Unit = {},
    onDetailsClick: (Movimiento) -> Unit = {}
) {
    val movimientos by viewModel.movimientos.collectAsState()
    val ingresoColor by viewModel.ingresoColor.collectAsState()
    val egresoColor by viewModel.egresoColor.collectAsState()
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(movimientos) {
        isLoading = false
    }

    Scaffold(
        topBar = { MisMovimientosTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isLoading = true
                    onAddClick()
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "AgregarMovimiento",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(46.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                }
                movimientos.isEmpty() -> {
                    Text(
                        text = "No tienes movimientos registrados",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(movimientos, key = { it.id }) { movimiento ->
                            MovimientoCard(
                                movimiento = movimiento,
                                ingresoColor = ingresoColor,
                                egresoColor = egresoColor,
                                onDelete = {
                                    scope.launch {
                                        isLoading = true
                                        viewModel.eliminar(movimiento)
                                    }
                                },
                                onEditClick = {
                                    isLoading = true
                                    onEditClick(movimiento)
                                },
                                onDetailsClick = { onDetailsClick(movimiento) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisMovimientosTopBar() {
    TopAppBar(
        title = {
            Text(
                "Mis movimientos",
                style = TextStyle(
                    fontSize = 31.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    )
}

@Composable
fun MovimientoCard(
    movimiento: Movimiento,
    ingresoColor: Color,
    egresoColor: Color,
    onDelete: () -> Unit,
    onEditClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    var estadoExpan by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(targetValue = if (estadoExpan) 180f else 0f)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (movimiento.tipo.lowercase() == "ingreso") ingresoColor else egresoColor
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = { estadoExpan = !estadoExpan }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val textFechaHora = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) {
                        append(" ${movimiento.fecha.format(formatter)}")
                    }
                }
                Text(
                    modifier = Modifier
                        .weight(6f)
                        .fillMaxWidth()
                        .padding(5.dp),
                    text = textFechaHora,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .alpha(0.6f)
                        .weight(1f)
                        .rotate(rotacion),
                    onClick = { estadoExpan = !estadoExpan }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Flecha de despliegue",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.background
                    )
                }
            }

            if (estadoExpan) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        val textTipo = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)) { append("Tipo:") }
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Light)) { append(" ${movimiento.tipo}") }
                        }
                        Text(text = textTipo, fontSize = 20.sp, modifier = Modifier.padding(4.dp))

                        val textMonto = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)) { append("Monto:") }
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Light)) { append(" ${movimiento.cantidad}") }
                        }
                        Text(text = textMonto, fontSize = 20.sp, modifier = Modifier.padding(4.dp))

                        val textMotivo = buildAnnotatedString {
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)) { append("Motivo:") }
                            withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Light)) { append(" ${movimiento.motivo ?: ""}") }
                        }
                        Text(text = textMotivo, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(onClick = onDetailsClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.vermas),
                                contentDescription = "Detalles",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = onDelete) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
