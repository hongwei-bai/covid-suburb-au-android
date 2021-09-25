package com.bhw.covid_suburb_au.util

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


object LocalJsonReader {
    fun loadJSONFromAsset(context: Context, fileName: String): String? =
        try {
            val `is`: InputStream = context.assets.open(fileName)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
}