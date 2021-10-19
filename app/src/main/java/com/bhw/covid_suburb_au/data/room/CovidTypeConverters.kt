package com.bhw.covid_suburb_au.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object CovidTypeConverters {
    @TypeConverter
    fun fromStringToLGACaseReport(value: String?): List<LGACaseReport>? {
        val listType: Type = object : TypeToken<List<LGACaseReport>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromLGACaseReport(list: List<LGACaseReport>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToMobileCovidAuCaseByStateEntity(value: String?): List<CovidAuCaseByStateEntity>? {
        val listType: Type = object : TypeToken<List<CovidAuCaseByStateEntity>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMobileCovidAuCaseByStateEntity(list: List<CovidAuCaseByStateEntity>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToMobileCovidAuCaseByLgaEntity(value: String?): List<CovidAuCaseByLgaEntity>? {
        val listType: Type = object : TypeToken<List<CovidAuCaseByLgaEntity>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromMobileCovidAuCaseByLgaEntity(list: List<CovidAuCaseByLgaEntity>?): String? {
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

    @TypeConverter
    fun fromStringToIntList(value: String?): List<Int> {
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIntList(list: List<Int>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToSettingsEntity(value: String?): SettingsEntity? {
        val type: Type = object : TypeToken<SettingsEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromSettingsEntity(entity: SettingsEntity?): String? {
        val gson = Gson()
        return gson.toJson(entity)
    }
}