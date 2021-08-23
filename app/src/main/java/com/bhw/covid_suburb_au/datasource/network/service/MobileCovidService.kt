package com.bhw.covid_suburb_au.datasource.network.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MobileCovidService {
    @GET("raw.do")
    suspend fun getRawData(
        @Query("days") days: Int,
        @Query("dataVersion") dataVersion: Long = -1,
        @Query("tops") tops: Int,
        @Query("followedSuburbs") followedSuburbs: List<Long>
    ): Response<Any>
}