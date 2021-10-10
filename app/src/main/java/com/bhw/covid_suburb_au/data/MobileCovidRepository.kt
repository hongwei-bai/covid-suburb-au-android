package com.bhw.covid_suburb_au.data

import androidx.room.withTransaction
import com.bhw.covid_suburb_au.data.network.model.MobileCovidAuRawMapper.mapToEntity
import com.bhw.covid_suburb_au.data.network.service.MobileCovidService
import com.bhw.covid_suburb_au.data.room.CovidAuEntity
import com.bhw.covid_suburb_au.data.room.CovidDatabase
import com.bhw.covid_suburb_au.data.util.Resource
import com.bhw.covid_suburb_au.data.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MobileCovidRepository @Inject constructor(
    private val mobileCovidService: MobileCovidService,
    private val db: CovidDatabase,
) {
    private val covidAuDao = db.covidAuDao()

    fun getMobileCovidRawData(): Flow<Resource<CovidAuEntity>> = networkBoundResource(
        query = {
            covidAuDao.getRawDataFlow()
        },
        fetch = {
            mobileCovidService.getRawData(1, 0L, 3, listOf(2118, 2075))
        },
        saveFetchResult = {
            db.withTransaction {
                covidAuDao.clear()
                it.body()?.let { data ->
                    covidAuDao.save(data.mapToEntity())
                }
            }
        }
    )

//    suspend fun fetchMobileCovidRawData() {
//        val rawDataDb = covidAuDao.getRawData()
//        val lastUpdateDaysDiff = LocalDateTimeUtil.getDayDiffFromToday(rawDataDb?.lastUpdate) ?: Long.MAX_VALUE
//        if (lastUpdateDaysDiff > 1) {
//            fetchMobileCovidRawDataFromBackend(rawDataDb?.dataVersion ?: -1)
//        }
//    }
//
//    suspend fun forceFetchMobileCovidRawData() {
//        val rawDataDb = covidAuDao.getRawData()
//        fetchMobileCovidRawDataFromBackend(rawDataDb?.dataVersion ?: -1)
//    }
//
//    private suspend fun fetchMobileCovidRawDataFromBackend(dataVersion: Long = -1L) {
//        Timber.w("fetch COVID raw data.(current ver: ${dataVersion})")
//        val response = mobileCovidService.getRawData(1, dataVersion, 3, listOf(2118, 2075))
//        val data = response.body()
//        if (response.isSuccessful && data is MobileCovidAuRawResponse) {
//            Timber.w("saved new COVID raw data(${data.dataVersion}) to db.")
//            covidAuDao.save(data.mapToEntity())
//        } else {
//            Timber.e("http failure response: $response")
//            userErrorChannel.trySend(ApiError(response.code(), response.message()))
//        }
//    }

//    fun getMobileCovidRawData(): Flow<CovidAuEntity> {
//        return covidAuDao.getRawDataFlow().onEach {
//            it ?: fetchMobileCovidRawDataFromBackend()
//        }.filterNotNull()
//    }
}