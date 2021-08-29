package com.bhw.covid_suburb_au.repository

import android.content.Context
import com.bhw.covid_suburb_au.datasource.helper.PostcodeToStateMap
import com.bhw.covid_suburb_au.datasource.model.AuState
import com.bhw.covid_suburb_au.datasource.room.AuPostcodeDao
import com.bhw.covid_suburb_au.datasource.room.SettingsDao
import com.bhw.covid_suburb_au.datasource.room.SettingsEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val settingsDao: SettingsDao,
    private val auPostcodeDao: AuPostcodeDao
) {
    suspend fun getPersonalSettings(): Flow<SettingsEntity> =
        settingsDao.getSettingsFlow()

    suspend fun saveMyPostcode(
        postcode: Long,
        state: String = PostcodeToStateMap.toState(postcode)?.name ?: AuState.NSW.name
    ) {
        val record = settingsDao.getSettings()
        if (record != null) {
            record.myPostcode = postcode
            record.myState = state
            settingsDao.save(record)
        } else {
            settingsDao.save(
                SettingsEntity(myPostcode = postcode, myState = state)
            )
        }
    }

    suspend fun saveFollowedPostcodes(list: List<Long>) {
        val record = settingsDao.getSettings()
        if (record != null) {
            record.followedPostcodes.clear()
            record.followedPostcodes.addAll(list)
            settingsDao.save(record)
        }
    }
}