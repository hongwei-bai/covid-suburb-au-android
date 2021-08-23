package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mobileCovidRepository: MobileCovidRepository
) : ViewModel() {
    val teamTheme: LiveData<Any> =
        mobileCovidRepository.getMobileCovidRawData()
            .asLiveData(viewModelScope.coroutineContext)
}