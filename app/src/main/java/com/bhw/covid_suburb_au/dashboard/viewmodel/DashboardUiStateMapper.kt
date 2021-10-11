package com.bhw.covid_suburb_au.dashboard.viewmodel

import com.bhw.covid_suburb_au.AppConfigurations
import com.bhw.covid_suburb_au.AppConfigurations.Configuration.STATE_HIGHLIGHT_THRESHOLD
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.data.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.data.model.AuState
import com.bhw.covid_suburb_au.data.room.CovidAuCaseByStateEntity
import com.bhw.covid_suburb_au.data.room.CovidAuEntity
import com.bhw.covid_suburb_au.data.room.SettingsEntity
import com.bhw.covid_suburb_au.data.util.Resource

object DashboardUiStateMapper {
    fun Resource<CovidAuEntity>.mapToUiState(myState: String?): BasicUiState = when (this) {
        is Resource.Loading -> BasicUiState.Loading
        is Resource.Success -> data.mapToUiState(myState)?.let {
            BasicUiState.Success(data.lastUpdate, it)
        } ?: BasicUiState.Error
        is Resource.Error -> BasicUiState.Error
    }

    private fun CovidAuEntity.mapToUiState(myState: String?): CasesByStateViewObject? =
        dataByDay.firstOrNull()?.caseByState?.run {
            val myState = myState
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

    private fun mapToStateViewObject(stateCode: String, entity: CovidAuCaseByStateEntity?, myState: String?): StateItemViewObject =
        StateItemViewObject(
            stateCode = stateCode,
            stateFullName = entity?.stateName ?: stateCode,
            isMyState = myState != null && entity?.stateCode == myState,
            cases = entity?.cases ?: 0,
            isHighlighted = entity?.cases ?: 0 >= STATE_HIGHLIGHT_THRESHOLD
        )

    suspend fun getSuburbList(
        settings: SettingsEntity?,
        data: CovidAuEntity,
        postcodeRepo: AuPostcodeRepository
    ): MutableList<SuburbUiState> =
        data.dataByDay.first().caseByPostcode.mapNotNull { postcodeRawData ->
            val postcodeInfo = postcodeRepo.getPostcode(postcodeRawData.postcode)
            val myPostcode = settings?.myPostcode
            val followedPostcodes = settings?.followedPostcodes
            postcodeInfo?.let { entity ->
                SuburbUiState(
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
                    isHighlighted = postcodeRawData.cases >= AppConfigurations.Configuration.SUBURB_HIGHLIGHT_THRESHOLD,
                    isMySuburb = myPostcode == entity.postcode,
                    isFollowed = followedPostcodes?.contains(entity.postcode) ?: false
                )
            }
        }.toMutableList()

    fun MutableList<SuburbUiState>.addMySuburbToList(settings: SettingsEntity) {
        add(
            SuburbUiState(
                rank = Int.MAX_VALUE,
                postcode = settings.myPostcode,
                briefName = settings.mySuburb ?: "",
                cases = 0,
                isMySuburb = true
            )
        )
    }

    suspend fun MutableList<SuburbUiState>.addFollowedSuburbToList(
        postcodeRepo: AuPostcodeRepository,
        postcode: Long
    ) {
        add(
            SuburbUiState(
                rank = Int.MAX_VALUE,
                postcode = postcode,
                briefName = postcodeRepo.getPostcode(postcode)?.suburbs?.let { list ->
                    AuSuburbHelper.getSuburbBrief(list.map { it.suburb }) ?: postcode.toString()
                } ?: postcode.toString(),
                cases = 0,
                isFollowed = true
            )
        )
    }
}