package com.bhw.covid_suburb_au.util

import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.util.*

class LocalDateTimeUtilTest {
    @Test
    fun testDateParse() {
        val dateString = "24 August 2021"
        val date = LocalDateTimeUtil.parseDate(dateString)
        val actualResult = Calendar.getInstance().apply { time = date }
        val expectResult = getCalendarInstance(2021, Calendar.AUGUST, 24, 0, 0, 0)
        assertEquals(expectResult, actualResult)
        assertEquals(expectResult.get(Calendar.YEAR), actualResult.get(Calendar.YEAR))
        assertEquals(expectResult.get(Calendar.DAY_OF_YEAR), actualResult.get(Calendar.DAY_OF_YEAR))
        assertEquals(expectResult.get(Calendar.HOUR_OF_DAY), actualResult.get(Calendar.HOUR_OF_DAY))
        assertEquals(expectResult.get(Calendar.MINUTE), actualResult.get(Calendar.MINUTE))
        assertEquals(expectResult.get(Calendar.SECOND), actualResult.get(Calendar.SECOND))
        assertEquals(expectResult.get(Calendar.MILLISECOND), actualResult.get(Calendar.MILLISECOND))
    }

    @Test
    @Ignore
    fun testDayDiff() {
        val dateString = "24 August 2021"
        val actualResult = LocalDateTimeUtil.getDayDiffFromToday(dateString = dateString)
        assertEquals(1, actualResult)
    }

    @Test
    @Ignore
    fun testIsToday() {
        val dateString = "25 August 2021"
        val actualResult = LocalDateTimeUtil.isToday(dateString = dateString)
        assertEquals(true, actualResult)
    }

    companion object {
        private fun getCalendarInstance(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int) =
            Calendar.getInstance().apply {
                set(year, month, day, hour, minute, second)
                set(Calendar.MILLISECOND, 0)
            }
    }
}