package com.bhw.covid_suburb_au.data.helper

object CovidDisplayHelper {
    fun casesToDisplay(cases: Int) = if (cases > 0) {
        "+$cases"
    } else cases.toString()
}