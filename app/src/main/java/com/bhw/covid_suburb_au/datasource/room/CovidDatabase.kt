package com.bhw.covid_suburb_au.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CovidAuEntity::class], version = 1)
@TypeConverters(CovidTypeConverters::class)
abstract class CovidDatabase : RoomDatabase() {
    abstract fun covidAuDao(): CovidAuDao
}