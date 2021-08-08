package com.bhw.covid_suburb_au.datasource

sealed class UserConsentError

object NetworkError : UserConsentError()

object ApiError : UserConsentError()

object GenericError : UserConsentError()