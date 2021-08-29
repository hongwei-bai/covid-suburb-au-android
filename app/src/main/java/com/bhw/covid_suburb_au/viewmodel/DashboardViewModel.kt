package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.*
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.datasource.room.CovidAuDayEntity
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.view.dashboard.viewobject.DashboardViewObject
import com.bhw.covid_suburb_au.view.dashboard.viewobject.FollowedSuburbViewObject
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
                topSuburbs = mapTopPostcodes(raw.dataByDay.first()),
                followedSuburbs = mapFollowedPostcodes(raw.dataByDay.first())
            )
        }.asLiveData(viewModelScope.coroutineContext)

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            mobileCovidRepository.forceFetchMobileCovidRawData()
            isRefreshing.postValue(false)
        }
    }

    private suspend fun mapFollowedPostcodes(dayEntity: CovidAuDayEntity): List<FollowedSuburbViewObject> =
//        dayEntity.caseByPostcode.filter {  }
        emptyList()

    private suspend fun mapTopPostcodes(dayEntity: CovidAuDayEntity): List<TopSuburbViewObject> =
        dayEntity.caseByPostcode.filterIndexed { i, _ -> i <= 2 }
            .mapNotNull { postcodeRawData ->
                val postcodeInfo =
                    auPostcodeRepository.getPostcode(postcodeRawData.postcode)
                postcodeInfo?.let {
                    TopSuburbViewObject(
                        postcode = postcodeRawData.postcode,
                        briefName = AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb })
                            ?: postcodeInfo.suburbs.joinToString(","),
                        cases = postcodeRawData.cases,
                        isFollowed = false
                    )
                }
            }
}