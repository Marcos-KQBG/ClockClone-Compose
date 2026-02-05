package com.marcos.clockclone.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.marcos.clockclone.data.local.WorldCity

@Composable
fun WorldClockScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Lista de ejemplo basada en tus imágenes
    // Hemos añadido más ciudades importantes para el "Reloj Mundial"
    val cities = listOf(
        WorldCity(1, "Londres", "1 hora menos", "15:51", "8°", 51.5074, -0.1278),
        WorldCity(2, "Madrid", "Zona horaria local", "16:51", "12°", 40.4168, -3.7038),
        WorldCity(3, "París", "Zona horaria local", "16:51", "10°", 48.8566, 2.3522),
        WorldCity(4, "Roma", "Zona horaria local", "16:51", "14°", 41.9028, 12.4964),
        WorldCity(5, "Moscú", "2 horas más", "18:51", "-2°", 55.7558, 37.6173)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Título o reloj digital superior (estilo Samsung)
        Text(
            text = "16:51:00",
            fontSize = 48.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 32.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(cities) { city ->
                CityCard(city = city) {
                    // Navegación pendiente al mapa
                    // navController.navigate("map_detail/${city.id}")
                }
            }
        }
    }
}

@Composable
fun CityCard(city: WorldCity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1C1E), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = city.name,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = city.timeDiff,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = city.time,
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
            // Pequeño indicador de temperatura como en tu captura
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = city.temp,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}