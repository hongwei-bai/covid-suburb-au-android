package com.bhw.covid_suburb_au.constant

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 15000L
        const val HTTP_WRITE_TIMEOUT = 15000L
        const val HTTP_CONNECT_TIMEOUT = 15000L

        const val SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"

        object HttpCode {
            const val HTTP_OK = 200
            const val HTTP_DATA_UP_TO_DATE = 205
        }
    }

    object Room {
        const val API_VERSION = 1
    }
}