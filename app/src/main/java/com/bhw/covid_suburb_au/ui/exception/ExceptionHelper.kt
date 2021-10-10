package com.bhw.covid_suburb_au.ui.exception

import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

object ExceptionHelper  {
    val covidExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("Exception caught: ${throwable.localizedMessage}")
        Timber.e(throwable)
    }
}