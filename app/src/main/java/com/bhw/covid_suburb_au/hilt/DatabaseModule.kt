package com.bhw.covid_suburb_au.hilt

import android.content.Context
import androidx.room.Room
import com.bhw.covid_suburb_au.datasource.room.AuPostcodeDao
import com.bhw.covid_suburb_au.datasource.room.CovidDatabase
import com.bhw.covid_suburb_au.datasource.room.CovidAuDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideMobileCovidAuRawDao(covidDatabase: CovidDatabase): CovidAuDao {
        return covidDatabase.covidAuDao()
    }

    @Provides
    fun provideAuPostcodeDao(covidDatabase: CovidDatabase): AuPostcodeDao {
        return covidDatabase.auPostcodeDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CovidDatabase {
        return Room.databaseBuilder(
            appContext,
            CovidDatabase::class.java,
            "covid_database"
        ).build()
    }
}


