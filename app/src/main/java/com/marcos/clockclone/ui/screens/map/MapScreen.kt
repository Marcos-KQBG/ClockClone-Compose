package com.marcos.clockclone.ui.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    cityName: String,
    lat: Double,
    lng: Double,
    onBack: () -> Unit
) {
    // Configuramos la posición inicial de la cámara en la ciudad pulsada
    val cityLocation = LatLng(lat, lng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityLocation, 5f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar ciudad: $cityName", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { padding ->
        // El componente de Google Maps para Compose
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = MapType.NORMAL)
        ) {
            // Ponemos el pin (Marker) de la ciudad principal
            Marker(
                state = MarkerState(position = cityLocation),
                title = cityName,
                snippet = "Hora local"
            )

            // Pines de las otras ciudades importantes que pediste
            val otherCities = listOf(
                LatLng(48.8566, 2.3522) to "París",
                LatLng(55.7558, 37.6173) to "Moscú",
                LatLng(38.9072, -77.0369) to "Washington",
                LatLng(45.4215, -75.6972) to "Ottawa",
                LatLng(41.9028, 12.4964) to "Roma",
                LatLng(52.5200, 13.4050) to "Berlín"
            )

            otherCities.forEach { (position, title) ->
                Marker(
                    state = MarkerState(position = position),
                    title = title
                )
            }
        }
    }
}