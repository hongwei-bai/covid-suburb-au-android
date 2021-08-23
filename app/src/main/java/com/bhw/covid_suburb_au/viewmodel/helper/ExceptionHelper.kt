package com.bhw.covid_suburb_au.viewmodel.helper

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

object ExceptionHelper  {
    var postHandler: (() -> Unit)? = null

    val nbaExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
        Log.e("bbbb", Log.getStackTraceString(throwable))
        postHandler?.invoke()
    }
}