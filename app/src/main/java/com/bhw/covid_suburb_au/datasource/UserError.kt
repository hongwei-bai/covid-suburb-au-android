package com.bhw.covid_suburb_au.datasource

sealed class UserError

object NetworkError : UserError()

data class ApiError(val code: Int, val message: String?) : UserError()

object GenericError : UserError()