package com.marcos.clockclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.marcos.clockclone.data.local.AppDatabase
import com.marcos.clockclone.ui.navigation.NavGraph
import com.marcos.clockclone.ui.screens.detail.DetailViewModel
import com.marcos.clockclone.ui.screens.list.ListViewModel
import com.marcos.clockclone.ui.screens.map.MapViewModel
import com.marcos.clockclone.ui.screens.splash.SplashViewModel
import com.marcos.clockclone.ui.theme.ClockCloneTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializamos la base de datos Room
        // "alarms_db" es el nombre del archivo que se guardará en el dispositivo
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "alarms_db"
        ).build()

        val dao = db.alarmDao()

        // 2. Definimos la Factory personalizada
        // Esto es necesario porque tus ViewModels ahora reciben el 'dao' por parámetro
        val factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(ListViewModel::class.java) ->
                        ListViewModel(dao) as T
                    modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                        DetailViewModel(dao) as T
                    modelClass.isAssignableFrom(MapViewModel::class.java) ->
                        MapViewModel() as T
                    modelClass.isAssignableFrom(SplashViewModel::class.java) ->
                        SplashViewModel() as T
                    else -> throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
                }
            }
        }

        // 3. Obtenemos las instancias de los ViewModels usando la factory
        // Al usar 'by viewModels { factory }', evitamos el error de NoSuchMethodException
        val splashViewModel: SplashViewModel by viewModels { factory }
        val listViewModel: ListViewModel by viewModels { factory }
        val mapViewModel: MapViewModel by viewModels { factory }
        val detailViewModel: DetailViewModel by viewModels { factory }

        setContent {
            // Aplicamos tu tema personalizado
            ClockCloneTheme {
                // Invocamos el NavGraph con todos los ViewModels necesarios
                NavGraph(
                    splashViewModel = splashViewModel,
                    listViewModel = listViewModel,
                    mapViewModel = mapViewModel,
                    detailViewModel = detailViewModel
                )
            }
        }
    }
}