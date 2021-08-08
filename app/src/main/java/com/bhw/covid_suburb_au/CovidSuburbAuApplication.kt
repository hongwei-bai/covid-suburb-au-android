package com.bhw.covid_suburb_au

import androidx.multidex.MultiDexApplication
import com.bhw.covid_suburb_au.datasource.local.AppSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CovidSuburbAuApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppSettings.initialize(this)
    }
}