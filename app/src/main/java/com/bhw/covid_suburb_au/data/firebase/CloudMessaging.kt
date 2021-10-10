package com.bhw.covid_suburb_au.data.firebase

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class CloudMessaging @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun register() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e(task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Timber.d("firebase cloud msg, token: $token")
        })
    }
}