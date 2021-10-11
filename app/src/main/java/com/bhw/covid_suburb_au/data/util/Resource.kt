package com.bhw.covid_suburb_au.data.util

sealed interface Resource<T> {
    data class Success<T>(val data: T, val new: Boolean = true) : Resource<T>
    class Loading<T> : Resource<T>
    data class Error<T>(val throwable: Throwable? = null) : Resource<T>
}