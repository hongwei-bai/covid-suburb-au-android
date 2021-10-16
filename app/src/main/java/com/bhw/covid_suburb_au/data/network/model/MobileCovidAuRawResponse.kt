package com.bhw.covid_suburb_au.data.network.model

data class MobileCovidAuRawResponse(
    val dataVersion: Long,
    val nationData: StateData?,
    val stateData: List<StateData>,
    var lgaData: List<StateLGAData>
)

data class StateData(
    val state: String,
    val totalCases: Long,
    val overseasCases: Long,
    val newCases: Long
)

data class StateLGAData(
    val lastUpdate: String?,
    val lastRecordTimeStamp: Long,
    val state: String,
    var lga: List<LGAData>
)

data class LGAData(
    val postcode: Int,
    val cases: Long
)