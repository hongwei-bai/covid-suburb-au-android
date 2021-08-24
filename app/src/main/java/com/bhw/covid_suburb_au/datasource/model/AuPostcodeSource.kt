package com.bhw.covid_suburb_au.datasource.model

data class AuPostcodeSource(
        val postcode: Long,
        val place_name: String,
        val state_name: String,
        val state_code: String,
        val latitude: Double,
        val longitude: Double,
        val accuracy: Int
)