package com.bhw.covid_suburb_au.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.data.MobileCovidRepository
import com.bhw.covid_suburb_au.ui.exception.ExceptionHelper.covidExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            mobileCovidRepository.getMobileCovidRawData()
            viewModelScope.launch(Dispatchers.Main + covidExceptionHandler) {
                onPreloadComplete.invoke()
            }
        }
    }
}