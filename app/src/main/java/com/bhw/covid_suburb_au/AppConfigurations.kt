package com.bhw.covid_suburb_au

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 15_000L
        const val HTTP_WRITE_TIMEOUT = 15_000L
        const val HTTP_CONNECT_TIMEOUT = 15_000L

        private const val SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"

        const val MOBILE_COVID_ENDPOINT = "$SERVICE_DOMAIN/application-service-covid/covid/au/"
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

        const val DASHBOARD_DATA_IN_DAY = 1
    }
}