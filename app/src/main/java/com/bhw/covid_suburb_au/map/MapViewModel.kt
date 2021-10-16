package com.bhw.covid_suburb_au.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.common.ExceptionHelper.covidExceptionHandler
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.data.MobileCovidRepository
import com.bhw.covid_suburb_au.data.SettingsRepository
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val mobileCovidRepository: MobileCovidRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val homeLocation = settingsRepository.getPersonalSettingsFlow()
        .map { settings ->
            settings?.myPostcode?.let {
                val mySuburb = settingsRepository.getPersonalSettings()?.mySuburb
                val location = auPostcodeRepository.getPostcode(settings.myPostcode)?.suburbs?.firstOrNull {
                    it.suburb == mySuburb
                }
                location?.let {
                    MapLocationUiState.Location(LatLng(it.latitude, it.longitude))
                } ?: MapLocationUiState.Unavailable
            } ?: MapLocationUiState.Unavailable
        }.asLiveData(viewModelScope.coroutineContext + covidExceptionHandler)
}