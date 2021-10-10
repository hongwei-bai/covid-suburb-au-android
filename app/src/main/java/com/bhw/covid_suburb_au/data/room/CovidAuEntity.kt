package com.bhw.covid_suburb_au.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhw.covid_suburb_au.AppConfigurations.Room.API_VERSION
import com.google.gson.annotations.SerializedName

@Entity(tableName = "covid_au_raw")
data class CovidAuEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,
    @SerializedName("data_version")
    val dataVersion: Long = -1,
    @SerializedName("last_update")
    val lastUpdate: String = "",
    @SerializedName("last_record_date")
    val lastRecordDate: String = "",
    @SerializedName("data_by_day")
    var dataByDay: List<CovidAuDayEntity>
)

data class CovidAuDayEntity(
    var date: Long = 0L,
    var caseByState: List<CovidAuCaseByStateEntity> = emptyList(),
    var caseExcludeFromStates: Int = 0,
    var caseTotal: Int = 0,
    var caseByPostcode: List<CovidAuCaseByPostcodeEntity> = emptyList()
)

data class CovidAuCaseByStateEntity(
    val stateCode: String = "",
    val stateName: String = "",
    val cases: Int = 0
)

data class CovidAuCaseByPostcodeEntity(
    val postcode: Long = 0L,
    val cases: Int = 0
)