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
import com.bhw.covid_suburb_au.util.LocalDateTimeUtil.getLocalDateTimeDisplay
import com.bhw.covid_suburb_au.util.LocalDateTimeUtil.parseDataVersion
import java.util.*

object DashboardUiStateMapper {
    fun Resource<CovidAuEntity>.mapToUiState(myState: String?): BasicUiState = when (this) {
        is Resource.Loading -> BasicUiState.Loading
        is Resource.Success -> data.mapToUiState(myState)?.let {
            BasicUiState.Success(data.dataVersion.toLocalDateTime(), it)
        } ?: BasicUiState.Error
        is Resource.Error -> BasicUiState.Error
    }

    private fun Long.toLocalDateTime(): String =
        if (this > 0) {
            parseDataVersion(toString())?.let {
                Calendar.getInstance().apply { time = it }
            }?.let {
                getLocalDateTimeDisplay(it)
            } ?: toString()
        } else toString()

    private fun CovidAuEntity.mapToUiState(myState: String?): CasesByStateViewObject? =
        caseByState.run {
            CasesByStateViewObject(
                nsw = mapToStateViewObject("NSW", firstOrNull { it.state == AuState.NSW.name }, myState),
                vic = mapToStateViewObject("VIC", firstOrNull { it.state == AuState.VIC.name }, myState),
                act = mapToStateViewObject("ACT", firstOrNull { it.state == AuState.ACT.name }, myState),
                wa = mapToStateViewObject("WA", firstOrNull { it.state == AuState.WA.name }, myState),
                sa = mapToStateViewObject("SA", firstOrNull { it.state == AuState.SA.name }, myState),
                tas = mapToStateViewObject("TAS", firstOrNull { it.state == AuState.TAS.name }, myState),
                nt = mapToStateViewObject("NT", firstOrNull { it.state == AuState.NT.name }, myState),
                qld = mapToStateViewObject("QLD", firstOrNull { it.state == AuState.QLD.name }, myState)
            )
        }

    private fun mapToStateViewObject(stateCode: String, entity: CovidAuCaseByStateEntity?, myState: String?): StateItemViewObject =
        StateItemViewObject(
            stateCode = stateCode,
            stateFullName = stateCode,
            isMyState = myState != null && entity?.state == myState,
            cases = entity?.newCases ?: 0,
            isHighlighted = entity?.newCases ?: 0 >= STATE_HIGHLIGHT_THRESHOLD
        )

    suspend fun getSuburbList(
        settings: SettingsEntity?,
        data: CovidAuEntity,
        postcodeRepo: AuPostcodeRepository
    ): MutableList<SuburbUiState> =
        data.caseByLga.mapNotNull { postcodeRawData ->
            val postcodeInfo = postcodeRepo.getPostcode(postcodeRawData.postcode)
            val myPostcode = settings?.myPostcode
            val followedPostcodes = settings?.followedPostcodes
            postcodeInfo?.let { entity ->
                SuburbUiState(
                    rank = data.caseByLga.indexOf(postcodeRawData),
                    postcode = postcodeRawData.postcode,
                    briefName = if (myPostcode == entity.postcode) {
                        settings.mySuburb ?: AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb })
                        ?: postcodeInfo.suburbs.joinToString(",")
                    } else {
                        AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb })
                            ?: postcodeInfo.suburbs.joinToString(",")
                    },
                    cases = postcodeRawData.newCases,
                    isHighlighted = postcodeRawData.newCases >= AppConfigurations.Configuration.SUBURB_HIGHLIGHT_THRESHOLD,
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
        postcode: Int
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