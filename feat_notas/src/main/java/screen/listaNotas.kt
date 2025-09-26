package screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dappsm.feat_notas.R
import datosFalsos.ViewModelClass

@Composable
fun NotasbottomBar() {
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
fun topbarNotas(){
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
        } ,
        actions = {
            IconButton(onClick = {/*Todo*/}) {
                Icon(
                    painter = painterResource(id= R.drawable.agregarnota),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }

        }
    )
}

@Composable
fun cardNotas(){
    var notas by remember{ mutableStateOf(ViewModelClass.movimientosRepo.obtenerNotas()) }
    Scaffold (topBar = { topbarNotas() },bottomBar = { NotasbottomBar() }){ innerPadding ->
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            items(notas, key = { it.id }) { nota ->
                val TextFecha = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer,fontWeight = FontWeight.SemiBold)) { append("Fecha:") }
                    withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${nota.fechayhoraNota}") } }
                val TextComentario = buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primaryContainer,fontWeight = FontWeight.SemiBold)) { append("Comentario:") }
                    withStyle(style= SpanStyle(color = MaterialTheme.colorScheme.primaryContainer, fontWeight = FontWeight.Light)) { append(" ${nota.comentario}") } }
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ){
                    Text(text = TextFecha, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
                    Text(text = TextComentario, fontSize = 20.sp, modifier = Modifier.padding(4.dp))
                    Spacer(modifier = Modifier.size(20.dp))
                    Divider(modifier = Modifier.width(600.dp).height(1.dp), color = MaterialTheme.colorScheme.primary)

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun listanotas() {
    /*FinmineTheme {
        cardNotas()
    }*/
}