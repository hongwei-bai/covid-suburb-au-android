package com.bhw.covid_suburb_au.repository

import com.bhw.covid_suburb_au.datasource.network.service.MobileCovidService
import com.bhw.covid_suburb_au.datasource.room.TeamThemeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MobileCovidRepository @Inject constructor(
    private val mobileCovidService: MobileCovidService,
    private val teamThemeDao: TeamThemeDao
) {
    suspend fun fetchMobileCovidRawDataFromBackend() {
        val response = mobileCovidService.getRawData(1, -1, 3, listOf(2118, 2075))
        val data = response.body()
        if (response.isSuccessful && data != null) {
//            teamThemeDao.save(data.map())
        }
    }

    fun getMobileCovidRawData(): Flow<Any> {
        return teamThemeDao.getTeamTheme().onEach {
            it ?: fetchMobileCovidRawDataFromBackend()
        }.filterNotNull()
    }
}