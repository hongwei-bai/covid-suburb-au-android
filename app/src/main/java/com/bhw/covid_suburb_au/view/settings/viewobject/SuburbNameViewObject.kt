package com.bhw.covid_suburb_au.view.settings.viewobject

data class SuburbNameViewObject(
    val selectedSuburb: String? = null,
    val suggestions: List<String> = emptyList()
)