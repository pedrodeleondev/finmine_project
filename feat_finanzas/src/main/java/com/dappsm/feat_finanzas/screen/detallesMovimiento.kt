package com.dappsm.feat_finanzas.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_finanzas.datosFalsos.ViewModelClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarDetails(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Detalles de",
                modifier= Modifier.fillMaxWidth().fillMaxHeight().padding( end = 3.dp),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 31.sp,
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier= Modifier.height(200.dp))
            Text(
                text = "movimiento",
                modifier= Modifier.fillMaxWidth().fillMaxHeight().padding(top= 35.dp, end = 3.dp),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 31.sp,
                    color = MaterialTheme.colorScheme.background,
                    fontWeight = FontWeight.Bold
                )
            )
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
    movimiento: ViewModelClass.Movimiento,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
){

    Scaffold(contentWindowInsets = WindowInsets(0.dp)){innerPadding ->
        Column(
            modifier= Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top

        ){
            Card( modifier = Modifier
                .fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)){
                Spacer(modifier = Modifier.height(8.dp))
                topBarDetails ( onBackClick )
                Spacer(modifier = Modifier.height(80.dp))
                Column(modifier= Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        text= movimiento.tipo.uppercase(),
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 15.sp,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val TextMonto = buildAnnotatedString {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.background,fontWeight = FontWeight.SemiBold)) { append("Monto:") }
                        withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light)) { append(" ${movimiento.monto} pesos") } }
                    Text(
                        text= TextMonto,
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(80.dp))

                }
            }
            Column(
                modifier= Modifier.fillMaxWidth().padding(20.dp)
            ){
                Spacer(modifier = Modifier.height(60.dp))
                val TextFecha = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer,fontWeight = FontWeight.SemiBold)) { append("Fecha:") }
                    withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${movimiento.fechayhora}") } }
                Text(
                    text=TextFecha,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
                val TextMetodo = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer,fontWeight = FontWeight.SemiBold)) { append("Método de pago:") }
                    withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${movimiento.metodopago}") } }
                Text(
                    text= TextMetodo,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
                val TextMotivo = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer,fontWeight = FontWeight.SemiBold)) { append("Motivo:") }
                    withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${movimiento.motivo}") } }
                Text(
                    text= TextMotivo,
                    fontSize = 24.sp,
                    maxLines= 3
                )

                Spacer(modifier=Modifier .width(300.dp).height(90.dp))

                Box( modifier = Modifier
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center){
                    Button(
                        onClick = { onEditClick() },
                        modifier = Modifier.width(300.dp).height(90.dp).padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        shape = RoundedCornerShape(10.dp)){

                        Icon(imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            modifier= Modifier.padding(end=8.dp).size(24.dp)
                        )
                        Text(
                            modifier= Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text= "Editar movimiento",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                    }
                }
            }

        }


    }

}

@Preview(showBackground = true)
@Composable
fun detallesMovimiento() {
    /*FinmineTheme {
        val movimientoEjemplo = ViewModelClass.movimientosRepo.obtenerMovimientos().first()
        DetallesMovimiento(movimiento = movimientoEjemplo,
            onBackClick = { *//*hola*//* },
            onEditClick = { *//* lógica editar *//* })
    }*/
}