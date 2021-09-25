package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.constant.AppConfigurations.Network.SPLASH_TIMEOUT
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.covidExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val mobileCovidRepository: MobileCovidRepository
) : ViewModel() {
    private var onStart = true

    fun preload(onPreloadComplete: () -> Unit) {
        if (!onStart) return
        onStart = false
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            if (!auPostcodeRepository.checkNeedInitialization()) {
                auPostcodeRepository.initialize()
            }
            mobileCovidRepository.fetchMobileCovidRawData()
            viewModelScope.launch(Dispatchers.Main + covidExceptionHandler) {
                onPreloadComplete.invoke()
            }
        }
    }
}