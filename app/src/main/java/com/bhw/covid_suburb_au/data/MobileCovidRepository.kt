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
}