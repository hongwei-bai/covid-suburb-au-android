package com.bhw.covid_suburb_au.dashboard.viewmodel


data class SuburbUiState(
    val rank: Int,
    val postcode: Int,
    var briefName: String,
    val cases: Long = 0,
    val isHighlighted: Boolean = false,
    val isFollowed: Boolean = false,
    val isMySuburb: Boolean = false,
)
