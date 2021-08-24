package com.bhw.covid_suburb_au.view.dashboard.viewobject

data class DashboardViewObject(
    val lastUpdate: String,
    val lastRecordDate: String,
    val topSuburbs: List<TopSuburbViewObject>,
    val followedSuburbs: List<FollowedSuburbViewObject>
)

data class TopSuburbViewObject(
    val postcode: Long,
    val briefName: String,
    val cases: Int
)

data class FollowedSuburbViewObject(
    val postcode: Long,
    val briefName: String,
    val cases: Int
)