package com.bhw.covid_suburb_au.dashboard.viewobject

data class CaseBySuburbViewObject(
    val list: List<SuburbItemViewObject> = emptyList()
)

sealed class AbstractSuburbItemViewObject

data class SuburbItemViewObject(
    val rank: Int,
    val postcode: Long,
    var briefName: String,
    val cases: Int = 0,
    val isHighlighted: Boolean = false,
    val isFollowed: Boolean = false,
    val isMySuburb: Boolean = false,
) : AbstractSuburbItemViewObject()

object ExpandableSeparator : AbstractSuburbItemViewObject()