package com.bhw.covid_suburb_au.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bhw.covid_suburb_au.constant.AppConfigurations.Room.API_VERSION
import kotlinx.coroutines.flow.Flow

@Dao
interface CovidAuDao {
    @Query("SELECT * FROM team_theme WHERE apiVersion=$API_VERSION")
    fun getTeamTheme(): Flow<CovidAuEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(covidAuEntity: CovidAuEntity)

    @Query("DELETE FROM team_theme")
    suspend fun clear()

    @Query("SELECT * FROM team_theme")
    fun getAllRecords(): List<CovidAuEntity>
}