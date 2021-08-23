package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.*
import com.bhw.covid_suburb_au.datasource.room.CovidAuEntity
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mobileCovidRepository: MobileCovidRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val covidRawData: LiveData<CovidAuEntity> =
        mobileCovidRepository.getMobileCovidRawData().asLiveData(viewModelScope.coroutineContext)

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            mobileCovidRepository.fetchMobileCovidRawDataFromBackend()
            isRefreshing.postValue(false)
        }
    }
}