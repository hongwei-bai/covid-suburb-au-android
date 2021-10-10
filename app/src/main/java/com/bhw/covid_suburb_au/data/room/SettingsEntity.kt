package com.bhw.covid_suburb_au.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bhw.covid_suburb_au.AppConfigurations.Room.API_VERSION
import com.google.gson.annotations.SerializedName


@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    @SerializedName("api_version")
    val apiVersion: Int = API_VERSION,

    @SerializedName("my_postcode")
    var myPostcode: Long = 0L,

    @SerializedName("my_suburb")
    var mySuburb: String?,

    @SerializedName("my_state")
    var myState: String = "",

    @SerializedName("followed_postcodes")
    val followedPostcodes: MutableList<Long> = mutableListOf()
)