package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.SettingsRepository
import com.bhw.covid_suburb_au.view.settings.viewobject.SuburbNameViewObject
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.covidExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val suburbName: MutableLiveData<SuburbNameViewObject?> = MutableLiveData(null)

    fun setInputPostcode(input: String) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            val number = input.trim().toLongOrNull()
            number?.let {
                val list = when (number) {
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
                    else -> emptyList()
                }
                if (list != null) {
                    suburbName.postValue(
                        SuburbNameViewObject(
                            selectedSuburb = null,
                            suggestions = list
                        )
                    )
                }
            }
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