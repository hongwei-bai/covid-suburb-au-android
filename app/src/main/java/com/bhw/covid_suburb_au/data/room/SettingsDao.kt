package com.bhw.covid_suburb_au.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bhw.covid_suburb_au.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE apiVersion=${API_VERSION}")
    suspend fun getSettings(): SettingsEntity?

    @Query("SELECT * FROM settings WHERE apiVersion=${API_VERSION}")
    fun getSettingsFlow(): Flow<SettingsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: SettingsEntity)

    @Query("DELETE FROM settings")
    suspend fun clear()
}