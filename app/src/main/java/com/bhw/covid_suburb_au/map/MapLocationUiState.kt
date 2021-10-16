package com.bhw.covid_suburb_au.map

import com.google.android.libraries.maps.model.LatLng


sealed interface MapLocationUiState {
    data class Location(val latLng: LatLng) : MapLocationUiState

    object Unavailable : MapLocationUiState

    object Loading : MapLocationUiState
}
