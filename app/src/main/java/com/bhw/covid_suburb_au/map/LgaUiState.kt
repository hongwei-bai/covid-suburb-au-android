package com.bhw.covid_suburb_au.map

import com.google.android.libraries.maps.model.LatLng

data class LgaUiState(
    val postcode: Int,

    val location: LatLng,

    val name: String,

    val newCases: Long
)