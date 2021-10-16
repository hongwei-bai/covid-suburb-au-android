package com.bhw.covid_suburb_au.map

enum class ZoomLevelGroup {
    Far, Near, Middle;

    companion object {
        fun getGroup(zoom: Float) =
            when {
                zoom > 12f -> Near
                zoom > 9f -> Middle
                else -> Far
            }

        fun isInSameGroup(group: ZoomLevelGroup, zoom2: Float): Boolean =
            group == getGroup(zoom2)

        fun isInSameGroup(zoom1: Float, zoom2: Float): Boolean =
            getGroup(zoom1) == getGroup(zoom2)
    }
}