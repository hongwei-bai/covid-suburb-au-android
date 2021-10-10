package com.bhw.covid_suburb_au.data.network.model

import com.bhw.covid_suburb_au.data.room.CovidAuCaseByPostcodeEntity
import com.bhw.covid_suburb_au.data.room.CovidAuCaseByStateEntity
import com.bhw.covid_suburb_au.data.room.CovidAuDayEntity
import com.bhw.covid_suburb_au.data.room.CovidAuEntity

object MobileCovidAuRawMapper {
    fun MobileCovidAuRawResponse.mapToEntity(): CovidAuEntity =
        CovidAuEntity(
            dataVersion = dataVersion,
            lastUpdate = lastUpdate,
            lastRecordDate = lastRecordDate,
            dataByDay = dataByDay.map { rawDataByDay ->
                CovidAuDayEntity(
                    date = rawDataByDay.date,
                    caseByState = rawDataByDay.caseByState.map {
                        CovidAuCaseByStateEntity(
                            stateCode = it.stateCode,
                            stateName = it.stateName,
                            cases = it.cases
                        )
                    },
                    caseExcludeFromStates = rawDataByDay.caseExcludeFromStates,
                    caseTotal = rawDataByDay.caseTotal,
                    caseByPostcode = rawDataByDay.caseByPostcode.map {
                        CovidAuCaseByPostcodeEntity(
                            postcode = it.postcode,
                            cases = it.cases
                        )
                    }
                )
            }
        )
}