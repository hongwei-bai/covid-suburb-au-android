package com.bhw.covid_suburb_au.dashboard.viewmodel

import androidx.lifecycle.*
import com.bhw.covid_suburb_au.AppConfigurations.Configuration.TOP
import com.bhw.covid_suburb_au.common.ExceptionHelper.covidExceptionHandler
import com.bhw.covid_suburb_au.dashboard.viewmodel.DashboardUiStateMapper.addFollowedSuburbToList
import com.bhw.covid_suburb_au.dashboard.viewmodel.DashboardUiStateMapper.addMySuburbToList
import com.bhw.covid_suburb_au.dashboard.viewmodel.DashboardUiStateMapper.getSuburbList
import com.bhw.covid_suburb_au.dashboard.viewmodel.DashboardUiStateMapper.mapToUiState
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.data.MobileCovidRepository
import com.bhw.covid_suburb_au.data.SettingsRepository
import com.bhw.covid_suburb_au.data.room.CovidAuEntity
import com.bhw.covid_suburb_au.data.room.SettingsEntity
import com.bhw.covid_suburb_au.data.util.Resource
import com.bhw.covid_suburb_au.util.LocalDateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val mobileCovidRepository: MobileCovidRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _basicUiState = MutableLiveData<BasicUiState>()

    val basicUiState: LiveData<BasicUiState> = _basicUiState

    private val _suburbLastUpdate = MutableLiveData<String?>()

    val suburbLastUpdate: LiveData<String?> = _suburbLastUpdate

    private val _suburbUiState = MutableLiveData<List<SuburbUiState>>()

    val suburbUiState: LiveData<List<SuburbUiState>> = _suburbUiState

    val isSuburbConfigured: LiveData<Boolean> = settingsRepository.getPersonalSettingsFlow().map {
        it?.myPostcode != null && it.myPostcode > 0L
    }.asLiveData(Dispatchers.IO)

    private val _isPostcodeInitialised = MutableLiveData(true)

    val isPostcodeInitialised: LiveData<Boolean> = _isPostcodeInitialised

    private val _isCompatList = MutableLiveData(true)

    val isCompatList: LiveData<Boolean> = _isCompatList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _basicUiState.postValue(BasicUiState.Loading)
            val initialised = auPostcodeRepository.checkIsInitialised()
            _isPostcodeInitialised.postValue(initialised)
            if (!initialised) {
                auPostcodeRepository.initialize()
                _isPostcodeInitialised.postValue(true)
            }
            query(true)

            settingsRepository.getPersonalSettingsFlow().onEach {
                refresh()
            }.collect()
        }
    }

    fun query(compatList: Boolean) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            _basicUiState.postValue(BasicUiState.Loading)
            val settings = settingsRepository.getPersonalSettings()
            val resource = mobileCovidRepository.getMobileCovidRawData()
            _basicUiState.postValue(resource.mapToUiState(settings?.myState))
            updateSuburbUi(settings, resource, compatList)
            _isCompatList.postValue(compatList)
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            _basicUiState.postValue(BasicUiState.Loading)
            val settings = settingsRepository.getPersonalSettings()
            val resource = mobileCovidRepository.forceFetchMobileCovidRawData()
            val compatList = isCompatList.value ?: true
            _basicUiState.postValue(resource.mapToUiState(settings?.myState))
            updateSuburbUi(settings, resource, compatList)
        }
    }

    private suspend fun updateSuburbUi(settings: SettingsEntity?, resource: Resource<CovidAuEntity>, compatList: Boolean) {
        var suburbLastUpdateString: String? = null
        if (resource is Resource.Success<CovidAuEntity>) {
            val fullList = getSuburbList(settings, resource.data, auPostcodeRepository)
            settings?.let {
                if (fullList.none { it.isMySuburb }) {
                    fullList.addMySuburbToList(settings)
                }
                settings.followedPostcodes.forEach { postcode ->
                    if (fullList.none { it.postcode == postcode }) {
                        fullList.addFollowedSuburbToList(auPostcodeRepository, postcode)
                    }
                }
            }
            val isSuburbConfigured = settings?.mySuburb != null
            if (compatList) {
                val compactList = if (isSuburbConfigured) {
                    fullList.filterIndexed { _, item ->
                        item.isMySuburb || item.isFollowed
                    }
                } else {
                    fullList.filterIndexed { index, item ->
                        index < TOP || item.isFollowed
                    }
                }
                _suburbUiState.postValue(compactList)
            } else {
                _suburbUiState.postValue(fullList)
            }
            _suburbLastUpdate.postValue(getCaseReportDate(settings, resource))
        }
    }

    private fun getCaseReportDate(settings: SettingsEntity?, resource: Resource.Success<CovidAuEntity>): String? {
        val caseReportDate = resource.data.lgaCaseReport.filterIndexed { index, lgaCaseReport ->
            settings?.myState?.let { myState ->
                myState == lgaCaseReport.state
            } ?: index == 0
        }.firstOrNull()?.reportDate
        return caseReportDate?.let {
            LocalDateTimeUtil.getLocalDateDisplay(Calendar.getInstance().apply {
                timeInMillis = caseReportDate
            })
        }
    }
}