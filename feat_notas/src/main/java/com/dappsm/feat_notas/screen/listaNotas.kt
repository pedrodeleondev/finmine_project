package com.dappsm.feat_notas.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import java.time.format.DateTimeFormatter

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
                    tint = MaterialTheme.colorScheme.primaryContainer,
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
    val notas by viewModel.notas.collectAsState()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(notas) {
        isLoading = false
    }

    Scaffold(
        topBar = { TopBarNotas(onAddClick) }
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
                notas.isEmpty() -> {
                    Text(
                        text = "No tienes notas registradas",
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
                        items(notas, key = { it.id }) { nota ->
                            NotaCard(nota = nota)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotaCard(nota: Nota) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val fechaFormateada = nota.fecha.format(formatter)

    val textFecha = buildAnnotatedString {
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.SemiBold)) { append("Fecha:") }
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" $fechaFormateada") }
    }

    val textComentario = buildAnnotatedString {
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.SemiBold)) { append("Comentario:") }
        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${nota.contenido}") }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(text = textFecha, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        Text(text = textComentario, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
        Spacer(modifier = Modifier.size(20.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}
