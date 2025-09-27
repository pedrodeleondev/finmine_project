package com.dappsm.feat_finanzas.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_finanzas.R
import com.dappsm.feat_finanzas.datosFalsos.ViewModelClass


fun convertirFecha(movimiento: ViewModelClass.Movimiento): String {
    val partes = movimiento.fechayhora.split(" ", "/")
    val mes = partes[1].toInt()
    val anio = partes[2]

    val meses = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    val nombreMes = meses[mes - 1]
    return "$nombreMes $anio"
}
fun agruparMovimientosPorMes(movimientos: List<ViewModelClass.Movimiento>): Map<String, List<ViewModelClass.Movimiento>> {
    return movimientos.groupBy { convertirFecha(it) }
}


@Composable
fun iEbottomBar() {
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
fun topbarIEM(){
    TopAppBar(
        title = {
            Column (horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth().padding(5.dp)){
                Text(
                    text= "Ingresos / Egresos",
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Bold
                    ))
                Text(
                    text="mensuales",
                    style = TextStyle(
                        fontSize = 31.sp,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    )
}

@Composable
fun ingresosEgresosMes(){
    Scaffold (topBar={topbarIEM()}, bottomBar = {iEbottomBar()},containerColor = MaterialTheme.colorScheme.background ){ innerPadding ->
        val movimientos = ViewModelClass.movimientosRepo.obtenerMovimientos()
        var mes by remember { mutableStateOf("") }
        var anio by remember { mutableStateOf("") }
        var mesError by remember { mutableStateOf(false) }
        var anioError by remember { mutableStateOf(false) }
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            Row(modifier= Modifier.padding(10.dp)){
                Column {
                    Text("Mes:", style = TextStyle(color= MaterialTheme.colorScheme.primaryContainer, fontSize= 19.sp ))

                    OutlinedTextField(
                        value = mes,
                        onValueChange = { input :String ->
                            val filtrado = input.filter { it.isDigit() }.take(2)
                            mes=filtrado
                            mesError= filtrado.isNotEmpty() && (filtrado.toInt() !in 1..12)},
                        modifier = Modifier.width(145.dp),
                        placeholder = {
                            Text(
                                "ej.09",
                                color =MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Normal,
                            )
                        },

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            cursorColor = MaterialTheme.colorScheme.onTertiary,
                            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary
                        ),
                        shape= RoundedCornerShape(10.dp),
                        isError = mesError
                    )
                    if (mesError){
                        Text("El mes debe de estar entre el 01 al 12",
                            color=MaterialTheme.colorScheme.error,
                            textAlign= TextAlign.Start,
                            fontSize= 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier= Modifier.padding(4.dp))
                    }
                }
                Spacer(modifier=Modifier.width(12.dp))
                Column {
                    Text("Año:",style = TextStyle(color= MaterialTheme.colorScheme.primaryContainer, fontSize= 19.sp ))
                    OutlinedTextField(
                        value = anio,
                        onValueChange = {input ->
                            val filtrado= input.filter { it.isDigit() }.take(4)
                            anio=filtrado
                            anioError = filtrado.length == 4 && filtrado.toInt() !in 1900..2100       },
                        modifier = Modifier.width(200.dp),
                        placeholder = {
                            Text(
                                "ej.2025",
                                color =MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Normal,
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary
                        ),
                        shape= RoundedCornerShape(10.dp),
                        isError = anioError
                    )
                    if(anioError){

                        Text("Introduce un año válido del 1900 al 2100",
                            color=MaterialTheme.colorScheme.error,
                            textAlign= TextAlign.Start,
                            fontSize= 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier= Modifier.padding(4.dp))

                    }

                }

            }
            val movimientosFiltrados = movimientos.filter { movimiento ->
                val partes = movimiento.fechayhora.split(" ", "/")
                val mesMov = partes[1]
                val anioMov = partes[2]

                (mes.isEmpty() || mesMov == mes) &&
                        (anio.isEmpty() || anioMov == anio)
            }

            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                val movimientosAgrupados = agruparMovimientosPorMes(movimientosFiltrados)
                movimientosAgrupados.forEach { (mesAnio, listaMovimientos) ->
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 10.dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Column( modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)){
                                Text(
                                    text = mesAnio,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.background,
                                    fontSize = 18.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(3.dp).fillMaxWidth()
                                )
                                listaMovimientos.forEach { movimiento ->
                                    val Textmovimiento = buildAnnotatedString {
                                        withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.background,fontWeight = FontWeight.Medium)) { append("${movimiento.tipo} de:") }
                                        withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light)) { append(" ${movimiento.monto}") } }
                                    Text(
                                        text=Textmovimiento ,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp,
                                        modifier=Modifier.padding(start = 10.dp,end=10.dp,top=10.dp, bottom = 2.dp)
                                    )
                                }}
                        }}

                }
            }


        }

    }

}
@Preview(showBackground = true)
@Composable
fun ingresosEgresos() {

       ingresosEgresosMes()

}




