package com.dappsm.feat_finanzas.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_finanzas.R
import com.dappsm.feat_finanzas.datosFalsos.ViewModelClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisMovimientosCard() {
    var movimientos by remember {
        mutableStateOf(ViewModelClass.movimientosRepo.obtenerMovimientos())
    }

    Scaffold(
        topBar = { MisMovimientosTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}, // TODO: lógica al click
                containerColor = MaterialTheme.colorScheme.tertiary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "AgregarNota",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(46.dp)
                )
            }
        },
        bottomBar = { MisMovimientosBottomBar() }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(movimientos, key = { it.id }) { movimiento ->
                MovimientoCard(movimiento = movimiento)
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
fun MovimientoCard(movimiento: ViewModelClass.Movimiento) {
    var estadoExpan by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(targetValue = if (estadoExpan) 180f else 0f)

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
            containerColor = if (movimiento.tipo == "Ingreso") { MaterialTheme.colorScheme.onErrorContainer
            } else {
                MaterialTheme.colorScheme.error
            }
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = { estadoExpan = !estadoExpan }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            // Header con fecha y flecha
            Row(verticalAlignment = Alignment.CenterVertically) {
                val textFechaHora = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)) { append(" ${movimiento.fechayhora}") }
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

            // Contenido expandido
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
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)) { append("Tipo:") }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Light)) { append(" ${movimiento.tipo}") }
                        }
                        Text(text = textTipo, fontSize = 20.sp, modifier = Modifier.padding(4.dp))

                        val textMonto = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)) { append("Monto:") }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Light)) { append(" ${movimiento.monto}") }
                        }
                        Text(text = textMonto, fontSize = 20.sp, modifier = Modifier.padding(4.dp))

                        val textMotivo = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Medium)) { append("Motivo:") }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Light)) { append(" ${movimiento.motivo}") }
                        }
                        Text(text = textMotivo, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
                    }


                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(onClick = { /* acción editar/mover */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.vermas),
                                contentDescription = "Editar",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = { /* acción eliminar */ }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                modifier = Modifier.size(24.dp)
                            )
                        } }
                }

            }
        }
    }
}

@Composable
fun MisMovimientosBottomBar() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.billos),
                    contentDescription = "Movimientos",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.filterlist),
                    contentDescription = "Lista filtrada",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.notsrecientes),
                    contentDescription = "Notas recientes",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.cuentausuario),
                    contentDescription = "Cuenta",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.sittings),
                    contentDescription = "Configuración",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified
                )
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun misMovimientos() {
    /*FinmineTheme {
        MisMovimientosCard()
    }*/
}





