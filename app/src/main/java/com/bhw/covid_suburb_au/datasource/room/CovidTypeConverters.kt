package com.bhw.covid_suburb_au.datasource.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object CovidTypeConverters {
    @TypeConverter
    fun fromStringToMobileCovidAuDayList(value: String?): List<CovidAuDayEntity>? {
        val listType: Type = object : TypeToken<List<CovidAuDayEntity>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMobileCovidAuDayList(list: List<CovidAuDayEntity>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToSuburbList(value: String?): List<AuSuburbEntity>? {
        val listType: Type = object : TypeToken<List<AuSuburbEntity>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromSuburbList(list: List<AuSuburbEntity>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}