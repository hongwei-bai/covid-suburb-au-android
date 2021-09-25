package com.bhw.covid_suburb_au.exception

sealed class CovidException : Throwable()

data class NetworkFailure(val code: Int, val _message: String) : CovidException()
