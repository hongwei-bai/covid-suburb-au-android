package com.bhw.covid_suburb_au.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.common.ExceptionHelper.covidExceptionHandler
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.data.MobileCovidRepository
import com.bhw.covid_suburb_au.data.SettingsRepository
import com.bhw.covid_suburb_au.data.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.data.util.Resource
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val mobileCovidRepository: MobileCovidRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    var currentLocation = MutableLiveData<LatLng>()

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

    val lgaWithCases = MutableLiveData<List<LgaUiState>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = when (val resource = mobileCovidRepository.getMobileCovidRawData()) {
                is Resource.Success -> resource.data.caseByLga.mapNotNull { lga ->
                    val postcodeInfo = auPostcodeRepository.getPostcode(lga.postcode)
                    postcodeInfo?.suburbs?.firstOrNull()?.let { suburb ->
                        LgaUiState(
                            postcode = lga.postcode,
                            location = LatLng(suburb.latitude, suburb.longitude),
                            name = AuSuburbHelper.getSuburbBrief(postcodeInfo.suburbs.map { it.suburb }) ?: "",
                            newCases = lga.newCases
                        )
                    }
                }
                else -> emptyList()
            }
            lgaWithCases.postValue(list)
        }
    }

    fun setLocation(location: LatLng) {
        currentLocation.value = location
    }
}