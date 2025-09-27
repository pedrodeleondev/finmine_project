package com.dappsm.feat_notas.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dappsm.data_core.model.Nota
import com.dappsm.feat_notas.R
import com.dappsm.feat_notas.viewmodel.NotaUiViewModel

@Composable
fun NotasBottomBar() {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNotas(onAddClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "Notas",
                style = TextStyle(
                    fontSize = 31.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        actions = {
            IconButton(onClick = { onAddClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.agregarnota),
                    contentDescription = "Agregar nota",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

@Composable
fun ListaNotas(
    onAddClick: () -> Unit,
    viewModel: NotaUiViewModel = viewModel()
) {
    val notas = viewModel.notas.collectAsState()

    Scaffold(
        topBar = { TopBarNotas(onAddClick) },
        bottomBar = { NotasBottomBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(notas.value, key = { it.id }) { nota ->
                NotaCard(nota = nota)
            }
        }
    }
}

@Composable
fun NotaCard(nota: Nota) {
    val textFecha = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        ) { append("Fecha:") }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Light
            )
        ) { append(" ${nota.fecha}") }
    }

    val textComentario = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        ) { append("Comentario:") }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Light
            )
        ) { append(" ${nota.contenido}") }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(text = textFecha, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        Text(text = textComentario, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        Spacer(modifier = Modifier.size(20.dp))
        Divider(
            modifier = Modifier
                .width(600.dp)
                .height(1.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
