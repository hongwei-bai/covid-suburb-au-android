package com.bhw.covid_suburb_au.settings.viewobject

data class SuburbMultipleSelectionListItem(
    val postcode: Int,
    val display: String,
    val isSelectable: Boolean = true,
    var isSelected: Boolean = false
)
