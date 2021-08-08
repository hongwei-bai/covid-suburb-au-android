package com.bhw.covid_suburb_au.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TeamThemeEntity::class], version = 1)
@TypeConverters
abstract class NbaDatabase : RoomDatabase() {
    abstract fun teamThemeDao(): TeamThemeDao
}