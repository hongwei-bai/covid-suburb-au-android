package com.bhw.covid_suburb_au.hilt

import android.content.Context
import com.bhw.covid_suburb_au.constant.AppConfigurations
import com.bhw.covid_suburb_au.constant.AppConfigurations.Network.HTTP_CONNECT_TIMEOUT
import com.bhw.covid_suburb_au.constant.AppConfigurations.Network.HTTP_READ_TIMEOUT
import com.bhw.covid_suburb_au.constant.AppConfigurations.Network.HTTP_WRITE_TIMEOUT
import com.bhw.covid_suburb_au.datasource.network.interceptor.PublicAccessInterceptor
import com.bhw.covid_suburb_au.datasource.network.service.MobileCovidService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providePublicAccessInterceptor(@ApplicationContext appContext: Context): PublicAccessInterceptor =
        PublicAccessInterceptor(appContext)

    @Provides
    @Singleton
    fun provideOkHttpClient(publicAccessInterceptor: PublicAccessInterceptor): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(publicAccessInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()

    @Provides
    @Singleton
    fun provideMobileCovidService(okHttpClient: OkHttpClient, moshi: Moshi): MobileCovidService {
        return Retrofit.Builder()
            .baseUrl(AppConfigurations.Network.MOBILE_COVID_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
            .create(MobileCovidService::class.java)
    }
}
