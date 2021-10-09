package com.bhw.covid_suburb_au.repository

import android.util.Log
import com.bhw.covid_suburb_au.datasource.ApiError
import com.bhw.covid_suburb_au.datasource.UserError
import com.bhw.covid_suburb_au.datasource.network.model.MobileCovidAuRawMapper.mapToEntity
import com.bhw.covid_suburb_au.datasource.network.model.MobileCovidAuRawResponse
import com.bhw.covid_suburb_au.datasource.network.service.MobileCovidService
import com.bhw.covid_suburb_au.datasource.room.CovidAuDao
import com.bhw.covid_suburb_au.datasource.room.CovidAuEntity
import com.bhw.covid_suburb_au.util.LocalDateTimeUtil
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MobileCovidRepository @Inject constructor(
    private val mobileCovidService: MobileCovidService,
    private val covidAuDao: CovidAuDao
) {
    private val userErrorChannel = Channel<UserError>()

    val userErrorFlow = userErrorChannel.receiveAsFlow()

    suspend fun fetchMobileCovidRawData() {
        val rawDataDb = covidAuDao.getRawData()
        val lastUpdateDaysDiff = LocalDateTimeUtil.getDayDiffFromToday(rawDataDb?.lastUpdate) ?: Long.MAX_VALUE
        if (lastUpdateDaysDiff > 1) {
            fetchMobileCovidRawDataFromBackend(rawDataDb?.dataVersion ?: -1)
        }
    }

    suspend fun forceFetchMobileCovidRawData() {
        val rawDataDb = covidAuDao.getRawData()
        fetchMobileCovidRawDataFromBackend(rawDataDb?.dataVersion ?: -1)
    }

    private suspend fun fetchMobileCovidRawDataFromBackend(dataVersion: Long = -1L) {
        Log.w("bbbb", "fetch COVID raw data.(current ver: ${dataVersion})")
        val response = mobileCovidService.getRawData(1, dataVersion, 3, listOf(2118, 2075))
        val data = response.body()
        if (response.isSuccessful && data is MobileCovidAuRawResponse) {
            Log.w("bbbb", "saved new COVID raw data(${data.dataVersion}) to db.")
            covidAuDao.save(data.mapToEntity())
        } else {
            Log.e("bbbb", "http failure response: $response")
            userErrorChannel.trySend(ApiError(response.code(), response.message()))
        }
    }

    fun getMobileCovidRawData(): Flow<CovidAuEntity> {
        return covidAuDao.getRawDataFlow().onEach {
            it ?: fetchMobileCovidRawDataFromBackend()
        }.filterNotNull()
    }
}