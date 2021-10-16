package com.bhw.covid_suburb_au.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "au_postcode")
data class AuPostcodeEntity(
    @PrimaryKey
    @SerializedName("postcode")
    val postcode: Int = 0,

    @SerializedName("suburbs")
    val suburbs: List<AuSuburbEntity> = emptyList()
)

data class AuSuburbEntity(
    @SerializedName("suburb")
    val suburb: String = "",

    @SerializedName("state")
    val state: String = "",

    @SerializedName("state_code")
    val stateCode: String = "",

    @SerializedName("latitude")
    val latitude: Double = 0.0,

    @SerializedName("longitude")
    val longitude: Double = 0.0,

    @SerializedName("accuracy")
    val accuracy: Int = 0
)