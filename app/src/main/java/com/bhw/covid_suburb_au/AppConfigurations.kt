package com.bhw.covid_suburb_au

import com.google.android.libraries.maps.model.LatLng

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 15_000L
        const val HTTP_WRITE_TIMEOUT = 15_000L
        const val HTTP_CONNECT_TIMEOUT = 15_000L

        private const val SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"

        const val MOBILE_COVID_ENDPOINT = "$SERVICE_DOMAIN/application-service-covid/covid-v2/au/"
    }

    object HttpCode {
        const val Ok = 200
        const val NoUpdate = 205
    }

    object Room {
        const val API_VERSION = 1
    }

    object Configuration {
        const val TOP = 3

        const val STATE_HIGHLIGHT_THRESHOLD = 100

        const val SUBURB_HIGHLIGHT_THRESHOLD = 10
    }

    object Map {
        val sydneyLocation = LatLng(-33.8678, 151.2073)
    }
}