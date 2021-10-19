package com.bhw.covid_suburb_au.data

import com.bhw.covid_suburb_au.AppConfigurations
import com.bhw.covid_suburb_au.data.network.model.MobileCovidAuRawMapper.mapToEntity
import com.bhw.covid_suburb_au.data.network.model.MobileCovidAuRawResponse
import com.bhw.covid_suburb_au.data.network.model.NewsResponse
import com.bhw.covid_suburb_au.data.network.service.MobileCovidService
import com.bhw.covid_suburb_au.data.room.CovidAuDao
import com.bhw.covid_suburb_au.data.room.CovidAuEntity
import com.bhw.covid_suburb_au.data.util.Resource
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class MobileCovidRepository @Inject constructor(
    private val mobileCovidService: MobileCovidService,
    private val covidAuDao: CovidAuDao,
    private val appMemoryCache: AppMemoryCache
) {
    suspend fun getMobileCovidRawData(): Resource<CovidAuEntity> =
        covidAuDao.getRawData()?.let {
            Resource.Success(it)
        } ?: fetchMobileCovidRawDataFromBackend()

    suspend fun getNews(): Resource<NewsResponse> = appMemoryCache.news?.let {
        Resource.Success(it)
    } ?: fetchNews()

    suspend fun forceFetchMobileCovidRawData(): Resource<CovidAuEntity> =
        fetchMobileCovidRawDataFromBackend(true)

    private suspend fun fetchNews(): Resource<NewsResponse> =
        try {
            appMemoryCache.news = null
            val response = mobileCovidService.getNews()
            val data = response.body()
            if (response.isSuccessful) {
                when {
                    response.code() == AppConfigurations.HttpCode.Ok && data != null -> {
                        appMemoryCache.news = data
                        Resource.Success(data)
                    }
                    else -> Resource.Error()
                }
            } else {
                Timber.e("http failure response: $response")
                Resource.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }

    private suspend fun fetchMobileCovidRawDataFromBackend(forceUpdate: Boolean = false): Resource<CovidAuEntity> =
        try {
            val cache = covidAuDao.getRawData()
            val requestDataVersion = if (forceUpdate) {
                cache?.dataVersion ?: -1
            } else {
                -1
            }
            Timber.i("fetch COVID raw data. forceUpdate: $forceUpdate (current ver: ${cache?.dataVersion})")
            val response = mobileCovidService.getRawData(
                dataVersion = requestDataVersion
            )
            val data = response.body()
            if (response.isSuccessful) {
                when {
                    response.code() == AppConfigurations.HttpCode.Ok && data is MobileCovidAuRawResponse -> data.mapToEntity().let {
                        Timber.i("saved new COVID raw data(${data.dataVersion}) to db.")
                        covidAuDao.save(it)
                        Resource.Success(it)
                    }
                    response.code() == AppConfigurations.HttpCode.NoUpdate && cache != null -> Resource.Success(cache, false)
                    else -> Resource.Error()
                }
            } else {
                Timber.e("http failure response: $response")
                Resource.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e)
        }
}