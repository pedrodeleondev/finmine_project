package com.dappsm.feat_finanzas.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_finanzas.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarCMovements(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Crear/Editar",
                modifier= Modifier.fillMaxWidth().fillMaxHeight().padding(top= 8.dp, end = 3.dp),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier= Modifier.height(200.dp))
            Text(
                text = "movimientos",
                modifier= Modifier.fillMaxWidth().fillMaxHeight().padding(top= 35.dp, end = 3.dp),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 31.sp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.billetos),
                    contentDescription = "Regresar",
                    modifier= Modifier.size(30.dp),
                )
            }
        }
    )
}
@Composable
fun formMovimiento( onBackClick: () -> Unit,
                    onEditClick: () -> Unit) {

    var id by remember { mutableStateOf("") }
    var fechayhora by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }
    var metodopago by remember { mutableStateOf("") }
    Column( modifier= Modifier

        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top) {

        topBarCMovements(onBackClick)
        Spacer(modifier= Modifier.height(100.dp))
        Text(text= "Tipo de movimiento:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color= MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth().padding(start=15.dp),
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
            shape= RoundedCornerShape(10.dp),
            value =id,
            onValueChange = { id = it },

            placeholder = {
                Text(
                    "ej. \"ingreso\"/\"egreso\"",
                    color =MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light,
                )
            },
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
        Text(text= "Monto:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color= MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
            shape= RoundedCornerShape(10.dp),
            value =monto,
            onValueChange = { monto = it },

            placeholder = {
                Text(
                    "ej. \"120 pesos\"",
                    color =MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light,
                )
            },
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
        Text(text= "Método de pago:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color= MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
            shape= RoundedCornerShape(10.dp),
            value =metodopago,
            onValueChange = { metodopago = it },

            placeholder = {
                Text(
                    "ej. \"Efectivo\"/\"Tarjeta\"",
                    color =MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light,
                )
            },
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
        Text(text= "Motivo:",
            fontSize = 20.sp,
            maxLines = 3,
            fontWeight = FontWeight.Light,
            color= MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(start=15.dp ,end= 15.dp),
            shape= RoundedCornerShape(10.dp),
            value =motivo,
            onValueChange = { motivo = it },

            placeholder = {
                Text(
                    "ej.\"Pago de préstamo\"",
                    color =MaterialTheme.colorScheme.background, fontWeight = FontWeight.Light,
                )
            },
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
        Spacer(modifier= Modifier.height(70.dp))
        Box( modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Button(
                onClick = { onEditClick() },
                modifier = Modifier.width(340.dp).height(74.dp).padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(10.dp)){

                Text(
                    modifier= Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    text= "Agregar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }
        }
    }

}




@Preview(showBackground = true)
@Composable
fun formmMovimiento() {
    /*Finmine_proyectTheme {
        formMovimiento(onBackClick = { *//*hola*//* },
            onEditClick = { *//* lógica editar *//* })
    }*/
}