package com.bhw.covid_suburb_au.data.model

data class AuPostcodeSource(
        val postcode: Long,
        val place_name: String,
        val state_name: String,
        val state_code: String,
        val latitude: Double,
        val longitude: Double,
        val accuracy: Int
)