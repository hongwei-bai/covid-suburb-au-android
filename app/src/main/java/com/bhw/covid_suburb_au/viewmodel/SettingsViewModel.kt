package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.datasource.room.AuPostcodeEntity
import com.bhw.covid_suburb_au.repository.AuPostcodeRepository
import com.bhw.covid_suburb_au.repository.SettingsRepository
import com.bhw.covid_suburb_au.viewmodel.helper.ExceptionHelper.covidExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

//    val suburbs: MutableLiveData<List<Pair<Long, String>>?> = MutableLiveData(emptyList())

    val suburb: LiveData<String?> =
        settingsRepository.getPersonalSettings().map {
            AuSuburbHelper.toDisplayString(it.myPostcode, it.mySuburb)
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

    val suburbs: Flow<PagingData<AuPostcodeEntity>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = true,
            maxSize = 5000
        )
    ) {
        auPostcodeRepository.getPostcodesPagingSource()
    }.flow.cachedIn(viewModelScope)

    fun updateSuburbInput(postcode: String) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            val r = auPostcodeRepository.getPostcodesPagingSource()
        }
    }

    fun updatePostcodeInput(input: String) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            val number = input.trim().toLongOrNull()
            val list = number?.let {
                when {
                    input.startsWith("00") -> null
                    input.startsWith("0") -> when (number) {
                        in 1..99 -> auPostcodeRepository.getPostcodeBriefByPrefix0xxx(number, 10)
                            .mapNotNull { AuSuburbHelper.toDisplayString(it.postcode, it.suburbs) }
                        in 100..999 -> auPostcodeRepository.getPostcode(number)?.let { postcodeEntity ->
                            postcodeEntity.suburbs.map {
                                AuSuburbHelper.toDisplayString(postcodeEntity.postcode, it.suburb)
                            }
                        }
                        else -> null
                    }
                    else -> when (number) {
                        in 0..999 -> auPostcodeRepository.getPostcodeBriefByPrefix(number, 10)
                            .mapNotNull { AuSuburbHelper.toDisplayString(it.postcode, it.suburbs) }
                        in 1000..9999 -> auPostcodeRepository.getPostcode(number)?.let { postcodeEntity ->
                            postcodeEntity.suburbs.map {
                                AuSuburbHelper.toDisplayString(postcodeEntity.postcode, it.suburb)
                            }
                        }
                        else -> null
                    }
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