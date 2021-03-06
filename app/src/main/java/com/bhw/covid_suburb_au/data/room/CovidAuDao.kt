package com.bhw.covid_suburb_au.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bhw.covid_suburb_au.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface CovidAuDao {
    @Query("SELECT * FROM covid_au_raw WHERE apiVersion=$API_VERSION")
    fun getRawDataFlow(): Flow<CovidAuEntity>

    @Query("SELECT * FROM covid_au_raw WHERE apiVersion=$API_VERSION")
    suspend fun getRawData(): CovidAuEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(covidAuEntity: CovidAuEntity)

    @Query("DELETE FROM covid_au_raw")
    suspend fun clear()

    @Query("SELECT * FROM covid_au_raw")
    fun getAllRecords(): List<CovidAuEntity>
}