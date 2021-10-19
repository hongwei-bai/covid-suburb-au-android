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

        const val POSTCODE_SEARCH_RESULT_LIMIT = 10
    }

    object Map {
        val sydneyLocation = LatLng(-33.8678, 151.2073)
    }

    object Cache {
        const val RETAIN_ALL_TABS_DURING_APP_ALIVE = 3
    }

    object About {
        object DataSource {
            const val NSW = "https://data.nsw.gov.au/data/dataset/nsw-covid-19-cases-by-location-and-likely-source-of-infection"
            const val VIC = "https://discover.data.vic.gov.au/dataset/victorian-covid-19-status-for-australian-local-government-area"
        }

        const val GITHUB_NEW_ISSUES = "https://github.com/hongwei-bai/covid-suburb-au-android/issues"
    }
}