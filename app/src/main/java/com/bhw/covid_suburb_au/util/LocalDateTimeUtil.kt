package com.bhw.covid_suburb_au.util

import androidx.annotation.VisibleForTesting
import java.text.SimpleDateFormat
import java.util.*

object LocalDateTimeUtil {
    private const val COVID_UPDATE_DISPLAY_DATE_FORMAT = "dd MMM yyyy"
    private const val COVID_DATA_VERSION_FORMAT = "yyyyMMddhh"
    private const val MILLIS_PER_DAY = 1000 * 3600 * 24L
    private const val COVID_UPDATE_DATE_TIME_FORMAT = "dd MMM yyyy 'at' HH:00 a"
    private const val NSW_GOV_DATE_FORMAT = "yyyy-MM-dd"

    fun getLocalDateTimeDisplay(calendar: Calendar, format: String = COVID_UPDATE_DATE_TIME_FORMAT): String =
        SimpleDateFormat(format, Locale.US).format(calendar.time)

    fun getLocalDateDisplay(calendar: Calendar, format: String = COVID_UPDATE_DISPLAY_DATE_FORMAT): String =
        SimpleDateFormat(format, Locale.US).format(calendar.time)

    fun isToday(dateString: String?): Boolean =
        parseDate(dateString)?.let { date ->
            val today = Calendar.getInstance()
            val calendar = Calendar.getInstance().apply { time = date }
            calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
        } ?: false

    fun getDayDiffFromToday(dateString: String?): Long? =
        parseDate(dateString)?.let { date ->
            val calendar = Calendar.getInstance().apply {
                time = date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            (today.timeInMillis - calendar.timeInMillis) / MILLIS_PER_DAY
        }

    @VisibleForTesting
    fun parseDate(dateString: String?, format: String = COVID_UPDATE_DISPLAY_DATE_FORMAT): Date? =
        dateString?.let {
            try {
                val simpleDateFormat = SimpleDateFormat(format)
                simpleDateFormat.parse(dateString)
            } catch (e: Exception) {
                null
            }
        }

    fun parseNSWDate(dateString: String?, format: String = NSW_GOV_DATE_FORMAT): Date? =
        dateString?.let {
            try {
                val simpleDateFormat = SimpleDateFormat(format)
                simpleDateFormat.parse(dateString)
            } catch (e: Exception) {
                null
            }
        }

    fun parseDataVersion(dataVersion: String?): Date? =
        dataVersion.let {
            try {
                val simpleDateFormat = SimpleDateFormat(COVID_DATA_VERSION_FORMAT)
                simpleDateFormat.parse(dataVersion)
            } catch (e: Exception) {
                null
            }
        }
}