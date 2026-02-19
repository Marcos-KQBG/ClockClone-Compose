package com.marcos.clockclone.ui.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcos.clockclone.data.local.WorldCity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class MapViewModel : ViewModel() {

    private val _cities = MutableStateFlow<List<WorldCity>>(emptyList())
    val cities = _cities.asStateFlow()

    private val _currentTime = MutableStateFlow(System.currentTimeMillis())
    val currentTime = _currentTime.asStateFlow()

    // Definimos la zona horaria de referencia como Madrid
    private val localTimeZone = TimeZone.getTimeZone("Europe/Madrid")

    private val initialCities = listOf(
        WorldCity(1, "Londres", "", "", "8°", 51.5074, -0.1278, "Europe/London"),
        WorldCity(2, "Madrid", "", "", "12°", 40.4168, -3.7038, "Europe/Madrid"),
        WorldCity(3, "Nueva York", "", "", "5°", 40.7128, -74.0060, "America/New_York"),
        WorldCity(4, "Tokio", "", "", "15°", 35.6895, 139.6917, "Asia/Tokyo"),
        WorldCity(5, "Sídney", "", "", "22°", -33.8688, 151.2093, "Australia/Sydney"),
        WorldCity(6, "París", "", "", "10°", 48.8566, 2.3522, "Europe/Paris"),
        WorldCity(7, "Pekín", "", "", "18°", 39.9042, 121.4737, "Asia/Shanghai")
    )

    init {
        // Timer para actualizar la hora cada segundo
        flow {
            while (true) {
                emit(System.currentTimeMillis())
                delay(1000)
            }
        }.onEach { now ->
            _currentTime.value = now
            _cities.value = initialCities.map { city ->
                updateCityTime(city, now)
            }.sortedBy { it.timeZoneId != localTimeZone.id } // <-- AQUÍ ESTÁ EL CAMBIO
        }.launchIn(viewModelScope)
    }

    private fun updateCityTime(city: WorldCity, currentTime: Long): WorldCity {
        val cityTimeZone = TimeZone.getTimeZone(city.timeZoneId)

        val timeFormat = SimpleDateFormat("HH:mm")
        timeFormat.timeZone = cityTimeZone
        val cityTime = timeFormat.format(Date(currentTime))

        val timeDiffMillis = cityTimeZone.getOffset(currentTime) - localTimeZone.getOffset(currentTime)
        val timeDiffHours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis.toLong())

        val timeDiffText = when {
            city.timeZoneId == localTimeZone.id -> "Zona horaria local"
            timeDiffHours == 0L -> "Misma hora"
            timeDiffHours > 0 -> "$timeDiffHours horas más"
            else -> "${-timeDiffHours} horas menos"
        }

        return city.copy(
            time = cityTime,
            timeDiff = timeDiffText
        )
    }
}