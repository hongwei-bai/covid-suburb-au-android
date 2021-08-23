package com.bhw.covid_suburb_au.datasource.network.model

data class MobileCovidAuRawResponse(
    val dataVersion: Long,
    val lastUpdate: String,
    val recordsCount: Int,
    val lastRecordDate: String,
    var dataByDay: List<MobileCovidAuDayResponse>
)

data class MobileCovidAuDayResponse(
    var date: Long,
    var caseByState: List<MobileCovidAuCaseByStateResponse> = emptyList(),
    var caseExcludeFromStates: Int = 0,
    var caseTotal: Int = 0,
    var caseByPostcode: List<MobileCovidAuCaseByPostcodeResponse> = emptyList()
)

data class MobileCovidAuCaseByStateResponse(
    val stateCode: String,
    val stateName: String,
    val cases: Int
)

data class MobileCovidAuCaseByPostcodeResponse(
    val postcode: Long,
    val cases: Int
)