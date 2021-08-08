package com.bhw.covid_suburb_au.hilt

import android.content.Context
import androidx.room.Room
import com.bhw.covid_suburb_au.datasource.room.NbaDatabase
import com.bhw.covid_suburb_au.datasource.room.TeamThemeDao
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
    fun provideTeamThemeDao(nbaDatabase: NbaDatabase): TeamThemeDao {
        return nbaDatabase.teamThemeDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NbaDatabase {
        return Room.databaseBuilder(
            appContext,
            NbaDatabase::class.java,
            "nba_database"
        ).build()
    }
}


