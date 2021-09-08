package com.bhw.covid_suburb_au.datasource.helper

import com.bhw.covid_suburb_au.datasource.room.AuSuburbEntity

object AuSuburbHelper {
    fun toDisplayString(postcode: Long, suburbs: List<AuSuburbEntity>): String? =
        getSuburbBrief(suburbs.map { it.suburb })?.let { toDisplayString(postcode, it) }

    fun toDisplayString(postcode: Long, suburbName: String?): String = "${postcode.toString().padStart(4, '0')} $suburbName"

    fun getSuburbBrief(suburbs: List<String>): String? = when (suburbs.size) {
        0 -> null
        1 -> suburbs.first()
        else -> {
            var result: String? = null
            // Priority No.1
            suburbs.forEachIndexed { i, suburbOut ->
                suburbs.forEachIndexed { j, suburbInner ->
                    if (i != j && suburbOut.contains(suburbInner)) {
                        result = suburbInner
                    }
                }
            }

            // Priority No.2
            if (result == null) {
                val wordRepeatMap = hashMapOf<String, Int>()
                suburbs.forEach {
                    val words = it.split(" ")
                    words.forEach { word ->
                        if (!SuburbMeaninglessWords.contains(word)) {
                            wordRepeatMap[word] = 1 + (wordRepeatMap[word] ?: 0)
                        }
                    }
                }
                val list = wordRepeatMap.toList().sortedByDescending { it.second }
                if (list.first().second >= 2) {
                    result = list.first().first
                }
            }

            result ?: suburbs.first()
        }
    }

    private val SuburbMeaninglessWords = listOf(
        "West",
        "East",
        "North",
        "South",
        "Park",
        "Hill",
        "Hills",
        "Point",
        "River",
        "Estate",
        "Old",
        "Mountain",
        "Valley",
        "Ridge",
        "Creek",
        "Island",
        "Junction",
        "Vale",
        "Bay",
        "Harbour",
        "Farm",
        "Cove",
        "Beach",
        "Centre",
        "Shore",
        "Hospital",
        "Head",
        "Airport",
        "Grove",
        "Heights",
        "Plateau",
        "Caves",
        "Town",
        "Forest",
        "Flat",
        "Walls",
        "Crossing",
        "DC"
    )
}