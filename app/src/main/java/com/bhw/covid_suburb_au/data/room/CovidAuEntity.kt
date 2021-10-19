package com.bhw.covid_suburb_au.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhw.covid_suburb_au.AppConfigurations.Room.API_VERSION
import com.google.gson.annotations.SerializedName

@Entity(tableName = "covid_au_raw")
data class CovidAuEntity(
    @PrimaryKey
    @SerializedName("apiVersion")
    val apiVersion: Int = API_VERSION,
    @SerializedName("dataVersion")
    val dataVersion: Long = -1,
    @SerializedName("totalNewCases")
    val totalNewCases: Long = -1,
    @SerializedName("caseByState")
    var caseByState: List<CovidAuCaseByStateEntity> = emptyList(),
    @SerializedName("caseByLga")
    var caseByLga: List<CovidAuCaseByLgaEntity> = emptyList(),
    @SerializedName("lgaCaseReport")
    var lgaCaseReport: List<LGACaseReport> = emptyList()
)

data class CovidAuCaseByStateEntity(
    @SerializedName("state")
    val state: String = "",
    @SerializedName("newCases")
    val newCases: Long = 0
)

data class CovidAuCaseByLgaEntity(
    @SerializedName("postcode")
    val postcode: Int = 0,
    @SerializedName("newCases")
    val newCases: Long = 0
)

data class LGACaseReport(
    @SerializedName("state")
    val state: String = "",
    @SerializedName("lastUpdate")
    val lastUpdate: Long? = null,
    @SerializedName("reportDate")
    val reportDate: Long = 0
)
