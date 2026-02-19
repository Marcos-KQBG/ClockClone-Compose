package com.marcos.clockclone.ui.screens.map

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    cityName: String,
    lat: Double,
    lng: Double,
    mapViewModel: MapViewModel = viewModel(),
    onBack: () -> Unit
) {
    val cities by mapViewModel.cities.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cityName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
                    val mapView = MapView(context)
                    mapView.setTileSource(TileSourceFactory.MAPNIK)
                    mapView.setMultiTouchControls(true)
                    mapView.minZoomLevel = 3.0
                    mapView.setHorizontalMapRepetitionEnabled(false)
                    mapView.setVerticalMapRepetitionEnabled(false)

                    // Establecemos los límites del mapa para que no se salga de la pantalla
                    val worldBounds = BoundingBox(85.0511, 180.0, -85.0511, -180.0)
                    mapView.setScrollableAreaLimitDouble(worldBounds)

                    mapView.controller.setZoom(12.0)
                    mapView.controller.setCenter(GeoPoint(lat, lng))
                    mapView
                },
                update = { mapView ->
                    mapView.overlays.clear()
                    cities.forEach { city ->
                        val marker = Marker(mapView)
                        marker.position = GeoPoint(city.latitude, city.longitude)
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        marker.title = city.name
                        mapView.overlays.add(marker)
                    }
                    mapView.controller.animateTo(GeoPoint(lat, lng))
                    mapView.invalidate() // Redraw the map
                }
            )
        }
    }
}