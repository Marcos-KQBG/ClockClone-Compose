package com.marcos.clockclone.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.marcos.clockclone.data.local.WorldCity

@Composable
fun WorldClockScreen(navController: NavController, mapViewModel: MapViewModel = viewModel(), modifier: Modifier = Modifier) {
    val cities = mapViewModel.cities

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "16:50:53", // Hora actual grande arriba
            fontSize = 48.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 32.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cities) { city ->
                CityCard(city = city) {
                    navController.navigate("map_detail/${city.name}/${city.latitude.toFloat()}/${city.longitude.toFloat()}")
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
            Text(text = city.name, fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = city.timeDiff, fontSize = 14.sp, color = Color.Gray)
        }
        Text(
            text = city.time,
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight.Light
        )
    }
}