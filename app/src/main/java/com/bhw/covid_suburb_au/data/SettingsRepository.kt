package com.bhw.covid_suburb_au.data

import android.content.Context
import com.bhw.covid_suburb_au.data.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.data.helper.PostcodeToStateMap
import com.bhw.covid_suburb_au.data.model.AuState
import com.bhw.covid_suburb_au.data.room.AuPostcodeDao
import com.bhw.covid_suburb_au.data.room.SettingsDao
import com.bhw.covid_suburb_au.data.room.SettingsEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val settingsDao: SettingsDao,
    private val auPostcodeDao: AuPostcodeDao
) {
    fun getPersonalSettingsFlow(): Flow<SettingsEntity?> =
        settingsDao.getSettingsFlow()

    suspend fun getPersonalSettings(): SettingsEntity? = settingsDao.getSettings()

    suspend fun getSuburbBriefByPostcode(postcode: Int): String? =
        auPostcodeDao.findPostcode(postcode)?.let { entity ->
            AuSuburbHelper.getSuburbBrief(entity.suburbs.map { it.suburb })
        }

    suspend fun getAllSuburbsByPostcode(postcode: Int): List<String>? =
        auPostcodeDao.findPostcode(postcode)?.let { entity ->
            entity.suburbs.map { it.suburb }
        }

    suspend fun saveMyPostcode(
        postcode: Int,
        suburb: String?,
        state: String = PostcodeToStateMap.toState(postcode)?.name ?: AuState.NSW.name
    ) {
        val record = settingsDao.getSettings()
        if (record != null) {
            record.myPostcode = postcode
            record.mySuburb = suburb
            record.myState = state
            settingsDao.save(record)
        } else {
            settingsDao.save(
                SettingsEntity(
                    myPostcode = postcode,
                    mySuburb = suburb,
                    myState = state
                )
            )
        }
    }

    suspend fun saveFollowedPostcodes(list: List<Int>) {
        val record = settingsDao.getSettings()
        if (record != null) {
            record.followedPostcodes.clear()
            record.followedPostcodes.addAll(list)
            settingsDao.save(record)
        }
    }
}