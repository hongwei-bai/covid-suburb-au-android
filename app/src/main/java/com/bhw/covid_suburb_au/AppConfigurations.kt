package com.bhw.covid_suburb_au

object AppConfigurations {
    object Network {
        const val HTTP_READ_TIMEOUT = 15_000L
        const val HTTP_WRITE_TIMEOUT = 15_000L
        const val HTTP_CONNECT_TIMEOUT = 15_000L

        const val SERVICE_DOMAIN = "https://hongwei-test1.top"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val AUTHORIZATION_BEARER = "Bearer"

        const val MOBILE_COVID_ENDPOINT = "$SERVICE_DOMAIN/application-service-covid/covid/au/"
    }

    object Room {
        const val API_VERSION = 1
    }
}