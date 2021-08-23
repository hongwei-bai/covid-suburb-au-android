package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.repository.MobileCovidRepository
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.nbaExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val mobileCovidRepository: MobileCovidRepository
) : ViewModel() {
    fun preload(onPreloadComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO + nbaExceptionHandler) {
            delay(100)
            mobileCovidRepository.fetchMobileCovidRawDataFromBackend()
            viewModelScope.launch(Dispatchers.Main + nbaExceptionHandler) {
                onPreloadComplete.invoke()
            }
        }
    }
}