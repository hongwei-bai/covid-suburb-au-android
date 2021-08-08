package com.bhw.covid_suburb_au.datasource.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhw.covid_suburb_au.constant.AppConfigurations.Room.API_VERSION
import com.google.gson.annotations.SerializedName

@Entity(tableName = "team_theme")
data class TeamThemeEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,
    @SerializedName("team")
    val team: String = "",
    @SerializedName("data_version")
    val dataVersion: Long = -1
)

