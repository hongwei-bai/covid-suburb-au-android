package com.bhw.covid_suburb_au.viewmodel.helper

import android.util.Log
import com.bhw.covid_suburb_au.LocalProperties
import kotlinx.coroutines.CoroutineExceptionHandler

object ExceptionHelper  {
    var postHandler: (() -> Unit)? = null

    val nbaExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (LocalProperties.isDebug) {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
            Log.e("bbbb", Log.getStackTraceString(throwable))
        } else {
            Log.e("bbbb", "Exception caught: ${throwable.localizedMessage}")
        }
        postHandler?.invoke()
    }
}