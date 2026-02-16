package com.marcos.clockclone.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {


    @Query("SELECT * FROM alarms ORDER BY id DESC")
    fun getAllAlarms(): Flow<List<Alarm>>

    // Busca una alarma específica por su ID
    @Query("SELECT * FROM alarms WHERE id = :id")
    suspend fun getAlarmById(id: Int): Alarm?

    // Inserta una alarma
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)

    // Borra la alarma
    @Delete
    suspend fun deleteAlarm(alarm: Alarm)
}