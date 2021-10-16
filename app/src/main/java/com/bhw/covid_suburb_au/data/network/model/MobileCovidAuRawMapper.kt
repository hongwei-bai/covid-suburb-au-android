package com.bhw.covid_suburb_au.data.network.model

import com.bhw.covid_suburb_au.data.model.AuState
import com.bhw.covid_suburb_au.data.room.CovidAuCaseByLgaEntity
import com.bhw.covid_suburb_au.data.room.CovidAuCaseByStateEntity
import com.bhw.covid_suburb_au.data.room.CovidAuEntity

object MobileCovidAuRawMapper {
    fun MobileCovidAuRawResponse.mapToEntity(): CovidAuEntity =
        CovidAuEntity(
            dataVersion = dataVersion,
            totalNewCases = nationData?.newCases ?: 0,
            caseByState = stateData.map {
                CovidAuCaseByStateEntity(
                    state = AuState.valueOf(it.state.uppercase()).name,
                    newCases = it.newCases
                )
            },
            caseByLga = lgaData.map { it.lga }.flatten()
                .sortedByDescending {
                    it.cases
                }
                .map {
                    CovidAuCaseByLgaEntity(
                        postcode = it.postcode,
                        newCases = it.cases
                    )
                }
        )
}