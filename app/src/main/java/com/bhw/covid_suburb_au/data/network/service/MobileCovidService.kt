package com.bhw.covid_suburb_au.data.network.service

import com.bhw.covid_suburb_au.data.network.model.MobileCovidAuRawResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MobileCovidService {
    @GET("raw.do")
    suspend fun getRawData(
        @Query("dataVersion") dataVersion: Long = -1,
        @Query("followedSuburbs") followedSuburbs: String? = null
    ): Response<MobileCovidAuRawResponse>
}