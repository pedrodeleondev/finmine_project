package com.dappsm.feat_config.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_config.R
import com.dappsm.feat_config.viewmodel.ConfigViewModel

val DefaultIngreso = Color(0xFFAFC357)
val DefaultEgreso = Color(0xFFD9585A)
val CakeIngreso = Color(0xFF4FC3F7)
val CakeEgreso = Color(0xFFF48FB1)
val DesertIngreso = Color(0xFFFFD54F)
val DesertEgreso = Color(0xFFFF8A65)

@Composable
fun ConfigScreen(viewModel: ConfigViewModel) {
    val darkMode by viewModel.isDarkMode.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Configuración",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.primaryContainer
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Selecciona el modo",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { viewModel.setDarkMode(true) },
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.noche),
                    contentDescription = "Modo Oscuro",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(60.dp)
                )
            }

            IconButton(
                onClick = { viewModel.setDarkMode(false) },
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dia),
                    contentDescription = "Modo Claro",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Cambio de colores",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primaryContainer
            )
        )
        Text(
            text = "En esta sección podrás cambiar el color de tus movimientos",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primaryContainer
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ColorOption(
                ingreso = DefaultIngreso,
                egreso = DefaultEgreso,
                label = "Default",
                desc = "verde-ingresos,\nrojo-egresos"
            ) { viewModel.setColors(DefaultIngreso, DefaultEgreso) }

            ColorOption(
                ingreso = CakeIngreso,
                egreso = CakeEgreso,
                label = "Cake",
                desc = "celeste-ingresos,\nrosa-egresos"
            ) { viewModel.setColors(CakeIngreso, CakeEgreso) }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ColorOption(
                ingreso = DesertIngreso,
                egreso = DesertEgreso,
                label = "Desert",
                desc = "amarillo-ingresos,\nnaranja-egresos"
            ) { viewModel.setColors(DesertIngreso, DesertEgreso) }
        }
    }
}

@Composable
fun ColorOption(
    ingreso: Color,
    egreso: Color,
    label: String,
    desc: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(ingreso)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(egreso)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$label:",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primaryContainer
            )
        )
        Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.primaryContainer
            ),
            textAlign = TextAlign.Center
        )
    }
}
