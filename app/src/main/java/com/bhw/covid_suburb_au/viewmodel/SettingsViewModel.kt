package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.*
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.SettingsRepository
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.covidExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val myPostcode: LiveData<Long?> =
        settingsRepository.getPersonalSettings().map {
            it.myPostcode
        }.asLiveData(Dispatchers.IO + covidExceptionHandler)

    val followedSuburbs: LiveData<List<Pair<Long, String>>?> =
        settingsRepository.getPersonalSettings().map {
            it.followedPostcodes.mapNotNull { postcode ->
                auPostcodeRepository.getPostcode(postcode)?.suburbs
                    ?.map { entity ->
                        entity.suburb
                    }?.let { suburbs ->
                        AuSuburbHelper.getSuburbBrief(suburbs)?.let { briefName ->
                            Pair(postcode, briefName)
                        }

                    }
            }
        }.asLiveData(Dispatchers.IO + covidExceptionHandler)

    val suburb: LiveData<String?> =
        settingsRepository.getPersonalSettings().map {
            "${it.myPostcode} ${it.mySuburb}"
        }.asLiveData(Dispatchers.IO + covidExceptionHandler)

    val suburbSuggestions: MutableLiveData<List<String>?> = MutableLiveData(null)

    fun setMySuburb(text: String) {
        val postcode = text.substring(0, 4).toLongOrNull() ?: return
        val suburb = text.substring(5)
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            settingsRepository.saveMyPostcode(
                postcode = postcode,
                suburb = suburb
            )
        }
    }

    fun updatePostcodeInput(input: String) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            val number = input.trim().toLongOrNull()
            val list = number?.let {
                when (number) {
                    in 0..999 -> auPostcodeRepository.getPostcodeBriefByPrefix(number, 10)
                        .map { postcode ->
                            "${postcode.postcode} ${AuSuburbHelper.getSuburbBrief(postcode.suburbs.map { it.suburb })}"
                        }
                    in 1000..9999 -> auPostcodeRepository.getPostcode(number)?.let { postcode ->
                        listOf(
                            "${postcode.postcode} ${AuSuburbHelper.getSuburbBrief(postcode.suburbs.map { it.suburb })}"
                        ) + postcode.suburbs.map {
                            "${postcode.postcode} ${it.suburb}"
                        }.filter { item ->
                            item != "${postcode.postcode} ${AuSuburbHelper.getSuburbBrief(postcode.suburbs.map { it.suburb })}"
                        }
                    }
                    else -> null
                }
            }
            suburbSuggestions.postValue(list ?: emptyList())
        }
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            // Do anything...
            isRefreshing.postValue(false)
        }
    }
}