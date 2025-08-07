// Paquete de la app
package com.example.laboratorio4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio4.ui.theme.Laboratorio4Theme


// Clase principal que representa la actividad
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()




        //Crea la lista de objetos mascotas que serán mostrados
        val listaAnimales = listOf(
            Mascota("Luky", "perro", "Pitbull", 2, false, R.drawable.luky), // Reemplaza R.drawable.luky con tu imagen
            Mascota("Rayman", "conejo", "Salvaje", 4, false, R.drawable.rayman),// Reemplaza R.drawable.rayman con tu imagen
            Mascota("Lucas", "pato", "Pekín", 3, false, R.drawable.patolucas) // Reemplaza R.drawable.lucas con tu imagen
        )
        //Define el contenido de la UI usando compose
        setContent {
            //Aplica el tema personalizado de la app
            Laboratorio4Theme {
                //Scafold da una estructura base a la pantalla
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   //Llama al composable que dibuja la lista de mascotas
                    ListaDeMascotas(
                        mascotas = listaAnimales,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


// ... otros imports

//composable que dibuja una tarjeta para cada mascota
@Composable
fun MascotaCard(mascota: Mascota, onAdoptarClicked: () -> Unit) { // 'mascota' aquí ya tiene el estado de adopción actual
    //crea una tarjeta visual con un poco de sombra
    Card(
        modifier = Modifier //Ocupa todo el ancho disponible
            .fillMaxWidth() //Margen exterior
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        //Organiza el contenido de la tarjeta en una fila horizontal
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),//Asegura que la fila ocupe todo el ancho
            verticalAlignment = Alignment.CenterVertically //Centra los elementos verticalmente
        ) {
            //Imagen de la mascota
            Image(
                painter = painterResource(id = mascota.imagenResId ?: R.drawable.ic_launcher_foreground),
                contentDescription = "Foto de ${mascota.nombre}",
                contentScale = ContentScale.Crop, //Recortar la imagen para que se ajuste sin distorsión
                modifier = Modifier
                    .size(80.dp) //Tamaña de la imagen
                    .clip(CircleShape) //Imagen circular
            )

            Spacer(modifier = Modifier.width(16.dp)) //Espacio entre imagen y texto
    //Columna con nombre y raza
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = mascota.nombre,//Nombre de la mascota
                    fontSize = 20.sp,//tamaño del texto
                    fontWeight = FontWeight.Bold//texto en negrita
                )
                Text(
                    text = mascota.raza,//Raza de la mascota
                    fontSize = 14.sp,
                    color = Color.Gray//Color gris para meos enfazis
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) //Espacio antes del botón

            //Botón para adoptar o mostrar estado de adopción
            Button(
                onClick = onAdoptarClicked,// Acción al hacer clic
                // Cambia el color del botón si está adoptado (opcional)
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (mascota.adoptado) Color.Gray else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = if (mascota.adoptado) "Adoptado" else "Adoptar",
                    // Cambia el tinte del ícono si está adoptado (opcional)
                    tint = if (mascota.adoptado) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else LocalContentColor.current
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                // AQUÍ ESTÁ EL CAMBIO PRINCIPAL: El texto del botón depende de mascota.adoptado
                Text(if (mascota.adoptado) "Adoptado" else "Adoptar")
            }
        }
    }
}


// ... otros imports

@Composable
fun ListaDeMascotas(mascotas: List<Mascota>, modifier: Modifier = Modifier) {


    LazyColumn(modifier = modifier) {

        items(mascotas.indices.toList(), key = { index -> mascotas[index].nombre }) { index ->

            var esAdoptadoLocal by remember(mascotas[index].nombre, mascotas[index].adoptado) {
                mutableStateOf(mascotas[index].adoptado)
            }

            MascotaCard(

                mascota = mascotas[index].copy(adoptado = esAdoptadoLocal),
                onAdoptarClicked = {
                    esAdoptadoLocal = !esAdoptadoLocal

                    println("${mascotas[index].nombre} ahora está: ${if (esAdoptadoLocal) "Adoptado" else "No adoptado"}")
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Laboratorio4Theme {
        val listaAnimalesPreview = listOf(
            Mascota("Luky Preview", "perro", "Pitbull", 2, false, null),
            Mascota("Rayman Preview", "conejo", "Salvaje", 4, true, null),
            Mascota("Lucas Preview", "pato", "Pekín", 3, false, null)
        )
        ListaDeMascotas(mascotas = listaAnimalesPreview)
    }
}
