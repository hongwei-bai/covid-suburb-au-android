package com.bhw.covid_suburb_au.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.datasource.model.AuState
import com.bhw.covid_suburb_au.datasource.room.CovidAuCaseByStateEntity
import com.bhw.covid_suburb_au.datasource.room.CovidAuEntity
import com.bhw.covid_suburb_au.datasource.room.SettingsEntity
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.repository.SettingsRepository
import com.bhw.covid_suburb_au.view.dashboard.viewobject.CaseBySuburbViewObject
import com.bhw.covid_suburb_au.view.dashboard.viewobject.CasesByStateViewObject
import com.bhw.covid_suburb_au.view.dashboard.viewobject.StateItemViewObject
import com.bhw.covid_suburb_au.view.dashboard.viewobject.SuburbItemViewObject
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.covidExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val mobileCovidRepository: MobileCovidRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val lastUpdate = mobileCovidRepository.getMobileCovidRawData().map { raw ->
        raw.lastUpdate
    }.asLiveData(viewModelScope.coroutineContext)

    val lastRecordDate = mobileCovidRepository.getMobileCovidRawData().map { raw ->
        raw.lastRecordDate
    }.asLiveData(viewModelScope.coroutineContext)

    val dataByState: LiveData<CasesByStateViewObject?> =
        settingsRepository.getPersonalSettingsFlow()
            .combine(mobileCovidRepository.getMobileCovidRawData()) { settings, data ->
                data.dataByDay.firstOrNull()?.caseByState?.run {
                    val myState = settings?.myState
                    CasesByStateViewObject(
                        nsw = mapToStateViewObject("NSW", firstOrNull { it.stateCode == AuState.NSW.name }, myState),
                        vic = mapToStateViewObject("VIC", firstOrNull { it.stateCode == AuState.VIC.name }, myState),
                        act = mapToStateViewObject("ACT", firstOrNull { it.stateCode == AuState.ACT.name }, myState),
                        wa = mapToStateViewObject("WA", firstOrNull { it.stateCode == AuState.WA.name }, myState),
                        sa = mapToStateViewObject("SA", firstOrNull { it.stateCode == AuState.SA.name }, myState),
                        tas = mapToStateViewObject("TAS", firstOrNull { it.stateCode == AuState.TAS.name }, myState),
                        nt = mapToStateViewObject("NT", firstOrNull { it.stateCode == AuState.NT.name }, myState),
                        qld = mapToStateViewObject("QLD", firstOrNull { it.stateCode == AuState.QLD.name }, myState)
                    )
                }
            }.asLiveData(viewModelScope.coroutineContext)

    val suburbsDataCompact: LiveData<CaseBySuburbViewObject> =
        settingsRepository.getPersonalSettingsFlow()
            .combine(mobileCovidRepository.getMobileCovidRawData()) { settings, data ->
                val fullList = getSuburbList(settings, data)
                settings?.let {
                    if (fullList.none { it.isMySuburb }) {
                        fullList.addMySuburbToList(settings)
                    }
                    if (settings.followedPostcodes.size <= MAX_FOLLOWED_SUBURBS_TO_DISPLAY_0_CASE) {
                        settings.followedPostcodes.forEach { postcode ->
                            if (fullList.none { it.postcode == postcode }) {
                                fullList.addFollowedSuburbToList(postcode)
                            }
                        }
                    }
                }

                val compactList = fullList.filterIndexed { index, item ->
                    index < TOP || item.isFollowed || item.isMySuburb
                }
                CaseBySuburbViewObject(compactList)
            }.asLiveData(viewModelScope.coroutineContext)

    val suburbsDataFull: LiveData<CaseBySuburbViewObject> =
        settingsRepository.getPersonalSettingsFlow()
            .combine(mobileCovidRepository.getMobileCovidRawData()) { settings, data ->
                val fullList = getSuburbList(settings, data)
                settings?.let {
                    if (fullList.none { it.isMySuburb }) {
                        fullList.addMySuburbToList(settings)
                    }
                    settings.followedPostcodes.forEach { postcode ->
                        if (fullList.none { it.postcode == postcode }) {
                            fullList.addFollowedSuburbToList(postcode)
                        }
                    }
                }
                CaseBySuburbViewObject(fullList)
            }.asLiveData(viewModelScope.coroutineContext)

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            mobileCovidRepository.forceFetchMobileCovidRawData()
            isRefreshing.postValue(false)
        }
    }

    private fun mapToStateViewObject(stateCode: String, entity: CovidAuCaseByStateEntity?, myState: String?): StateItemViewObject =
        StateItemViewObject(
            stateCode = stateCode,
            stateFullName = entity?.stateName ?: stateCode,
            isMyState = entity?.stateCode == myState,
            cases = entity?.cases ?: 0,
            isHighlighted = entity?.cases ?: 0 >= STATE_HIGHLIGHT_THRESHOLD
        )

    private suspend fun getSuburbList(settings: SettingsEntity?, data: CovidAuEntity): MutableList<SuburbItemViewObject> =
        data.dataByDay.first().caseByPostcode.mapNotNull { postcodeRawData ->
            val postcodeInfo = auPostcodeRepository.getPostcode(postcodeRawData.postcode)
            val myPostcode = settings?.myPostcode
            val followedPostcodes = settings?.followedPostcodes
            postcodeInfo?.let { entity ->
                SuburbItemViewObject(
                    rank = data.dataByDay.first().caseByPostcode.indexOf(postcodeRawData),
                    postcode = postcodeRawData.postcode,
                    briefName = if (myPostcode == entity.postcode) {
                        settings.mySuburb ?: AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb })
                        ?: postcodeInfo.suburbs.joinToString(",")
                    } else {
                        AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb })
                            ?: postcodeInfo.suburbs.joinToString(",")
                    },
                    cases = postcodeRawData.cases,
                    isHighlighted = postcodeRawData.cases >= SUBURB_HIGHLIGHT_THRESHOLD,
                    isMySuburb = myPostcode == entity.postcode,
                    isFollowed = followedPostcodes?.contains(entity.postcode) ?: false
                )
            }
        }.toMutableList()

    private fun MutableList<SuburbItemViewObject>.addMySuburbToList(settings: SettingsEntity) {
        add(
            SuburbItemViewObject(
                rank = Int.MAX_VALUE,
                postcode = settings.myPostcode,
                briefName = settings.mySuburb ?: "",
                cases = 0,
                isMySuburb = true
            )
        )
    }

    private suspend fun MutableList<SuburbItemViewObject>.addFollowedSuburbToList(postcode: Long) {
        add(
            SuburbItemViewObject(
                rank = Int.MAX_VALUE,
                postcode = postcode,
                briefName =
                auPostcodeRepository.getPostcode(postcode)?.suburbs?.let {
                    AuSuburbHelper.getSuburbBrief(it.map { it.suburb })
                        ?: postcode.toString()
                } ?: postcode.toString(),
                cases = 0,
                isFollowed = true
            )
        )
    }

    companion object {
        private const val TOP = 3

        /* It means that if you are following more than ${MAX_FOLLOWED_SUBURBS_TO_DISPLAY_0_CASE} suburbs,
            it won't display the suburbs you are following if cases is 0.*/
        private const val MAX_FOLLOWED_SUBURBS_TO_DISPLAY_0_CASE = 6

        private const val STATE_HIGHLIGHT_THRESHOLD = 100

        private const val SUBURB_HIGHLIGHT_THRESHOLD = 10
    }
}