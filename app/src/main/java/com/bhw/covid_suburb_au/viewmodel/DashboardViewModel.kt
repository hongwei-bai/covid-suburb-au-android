package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.*
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.view.dashboard.viewobject.DashboardViewObject
import com.bhw.covid_suburb_au.view.dashboard.viewobject.TopSuburbViewObject
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val mobileCovidRepository: MobileCovidRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val data: LiveData<DashboardViewObject> =
        mobileCovidRepository.getMobileCovidRawData().map { raw ->
            DashboardViewObject(
                lastUpdate = raw.lastUpdate,
                lastRecordDate = raw.lastRecordDate,
                topSuburbs = raw.dataByDay.first().caseByPostcode.filterIndexed { i, _ -> i <= 2 }
                    .mapNotNull { postcodeRawData ->
                        val postcodeInfo =
                            auPostcodeRepository.getPostcode(postcodeRawData.postcode)
                        postcodeInfo?.let {
                            TopSuburbViewObject(
                                postcode = postcodeRawData.postcode,
                                briefName = AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb })
                                    ?: postcodeInfo.suburbs.joinToString(","),
                                cases = postcodeRawData.cases
                            )
                        }
                    },
                followedSuburbs = emptyList()
            )
        }.asLiveData(viewModelScope.coroutineContext)

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            mobileCovidRepository.fetchMobileCovidRawData()
            isRefreshing.postValue(false)
        }
    }
}