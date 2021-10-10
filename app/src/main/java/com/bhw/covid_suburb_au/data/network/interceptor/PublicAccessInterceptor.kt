package com.bhw.covid_suburb_au.data.network.interceptor

import android.content.Context
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.AppConfigurations.Network.AUTHORIZATION_BEARER
import com.bhw.covid_suburb_au.AppConfigurations.Network.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class PublicAccessInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = context.getString(R.string.public_access_token);
        val request: Request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, "$AUTHORIZATION_BEARER $jwt")
            .build()
        return chain.proceed(newRequest)
    }
}