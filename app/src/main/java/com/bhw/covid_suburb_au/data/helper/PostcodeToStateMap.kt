package com.bhw.covid_suburb_au.data.helper

import com.bhw.covid_suburb_au.data.model.AuState

object PostcodeToStateMap {
    fun toState(postcode: Int?): AuState? =
        postcode?.let {
            when (postcode) {
                in 1000..1999 -> AuState.NSW
                in 2000..2599 -> AuState.NSW
                in 2619..2899 -> AuState.NSW
                in 2921..2999 -> AuState.NSW

                in 200..299 -> AuState.ACT
                in 2600..2618 -> AuState.ACT
                in 2900..2920 -> AuState.ACT

                in 3000..3999 -> AuState.VIC
                in 8000..8999 -> AuState.VIC

                in 4000..4999 -> AuState.QLD
                in 9000..9999 -> AuState.QLD

                in 5000..5799 -> AuState.SA
                in 5800..5999 -> AuState.SA

                in 6000..6797 -> AuState.WA
                in 6800..6999 -> AuState.WA

                in 7000..7799 -> AuState.TAS
                in 7800..7999 -> AuState.TAS

                in 800..899 -> AuState.NT
                in 900..999 -> AuState.NT

                else -> null
            }
        }
}