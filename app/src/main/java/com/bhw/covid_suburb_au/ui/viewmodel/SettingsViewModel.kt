package com.bhw.covid_suburb_au.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bhw.covid_suburb_au.data.AuPostcodeRepository
import com.bhw.covid_suburb_au.data.SettingsRepository
import com.bhw.covid_suburb_au.data.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.data.room.AuPostcodeEntity
import com.bhw.covid_suburb_au.ui.exception.ExceptionHelper.covidExceptionHandler
import com.bhw.covid_suburb_au.ui.settings.viewobject.SuburbMultipleSelectionListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auPostcodeRepository: AuPostcodeRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            settingsRepository.getPersonalSettings()?.run {
                initialiseAdjacentSuburbs(myPostcode, followedPostcodes)
            }
        }
    }

    private var followedSuburbSearchKeyword: String = ""

    val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)

    val myPostcode: LiveData<Long?> =
        settingsRepository.getPersonalSettingsFlow().mapNotNull {
            it?.myPostcode
        }.asLiveData(Dispatchers.IO + covidExceptionHandler)

    val followedSuburbs: LiveData<List<Pair<Long, String>>?> =
        settingsRepository.getPersonalSettingsFlow().map {
            it?.followedPostcodes?.mapNotNull { postcode ->
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

    val adjacentSuburbs: MutableLiveData<List<SuburbMultipleSelectionListItem>> = MutableLiveData(emptyList())

    val suburb: LiveData<String?> =
        settingsRepository.getPersonalSettingsFlow().mapNotNull { settings ->
            settings?.run {
                AuSuburbHelper.toDisplayString(myPostcode, mySuburb)
            }
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

            settingsRepository.getPersonalSettings()?.run {
                if (followedPostcodes.contains(postcode)) {
                    setFollowPostcode(postcode, false)
                }

                initialiseAdjacentSuburbs(postcode, followedPostcodes)
            }
        }
    }

    fun setFollowPostcode(postcode: Long, isFollow: Boolean) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            settingsRepository.getPersonalSettings()?.run {
                var isChanged = false
                val newList: MutableList<Long> = mutableListOf()
                newList.addAll(followedPostcodes)
                if (followedPostcodes.contains(postcode)) {
                    if (!isFollow) {
                        newList.remove(postcode)
                        isChanged = true
                    }
                } else {
                    if (isFollow) {
                        newList.add(postcode)
                        isChanged = true
                    }
                }
                if (isChanged) {
                    settingsRepository.saveFollowedPostcodes(newList)
                    updateFollowedSuburbsPickerInput(followedSuburbSearchKeyword)
                }
            }
        }
    }

    val suburbs: Flow<PagingData<AuPostcodeEntity>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = true,
            maxSize = 5000
        )
    ) {
        Timber.d("page load start")
        val r = auPostcodeRepository.getPostcodesPagingSource()
        Timber.d("page load completed: $r")
        r
    }.flow.cachedIn(viewModelScope)

    fun updateFollowedSuburbsPickerInput(inputString: String) {
        followedSuburbSearchKeyword = inputString
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            val myPostcode = settingsRepository.getPersonalSettings()?.myPostcode
            val followed = settingsRepository.getPersonalSettings()?.followedPostcodes
            val list = myPostcode?.let {
                when {
                    inputString.isEmpty() -> auPostcodeRepository.getPostcodes(myPostcode, POSTCODE_PICKER_LIST_RADIUS)
                    inputString.toLongOrNull() != null -> getPostcodeEntityListByNumber(inputString)
                    else -> emptyList()
                }
            }?.map { entity ->
                SuburbMultipleSelectionListItem(
                    postcode = entity.postcode,
                    display = AuSuburbHelper.toDisplayString(entity.postcode, entity.suburbs) ?: "",
                    isSelectable = entity.postcode != myPostcode,
                    isSelected = followed?.contains(entity.postcode) == true
                )
            }
            adjacentSuburbs.postValue(list)
        }
    }

    fun updateMySuburbPickerInput(input: String) {
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            val entities = getPostcodeEntityListByNumber(input)
            val list: List<String> = if (isAccurateMatch(input)) {
                entities.firstOrNull()?.let { postcodeEntity ->
                    postcodeEntity.suburbs.map {
                        AuSuburbHelper.toDisplayString(postcodeEntity.postcode, it.suburb)
                    }
                } ?: emptyList()
            } else {
                entities.mapNotNull { AuSuburbHelper.toDisplayString(it.postcode, it.suburbs) }
            }
            suburbSuggestions.postValue(list)
        }
    }

    fun refresh() {
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO + covidExceptionHandler) {
            // Do anything...
            isRefreshing.postValue(false)
        }
    }

    private suspend fun initialiseAdjacentSuburbs(myPostcode: Long, followed: List<Long>) {
        adjacentSuburbs.postValue(auPostcodeRepository.getPostcodes(myPostcode, POSTCODE_PICKER_LIST_RADIUS).map { entity ->
            SuburbMultipleSelectionListItem(
                postcode = entity.postcode,
                display = AuSuburbHelper.toDisplayString(entity.postcode, entity.suburbs) ?: "",
                isSelectable = entity.postcode != myPostcode,
                isSelected = followed.contains(entity.postcode)
            )
        })
    }

    private suspend fun getPostcodeEntityListByNumber(rawString: String, number: Long? = rawString.toLongOrNull()): List<AuPostcodeEntity> =
        number?.let {
            when {
                rawString.startsWith("00") -> emptyList()
                rawString.startsWith("0") -> when (number) {
                    in 1..99 -> auPostcodeRepository.getPostcodeBriefByPrefix0xxx(number, POSTCODE_PICKER_LIST_LENGTH)
                    in 100..999 -> listOfNotNull(auPostcodeRepository.getPostcode(number))
                    else -> emptyList()
                }
                else -> when (number) {
                    in 0..999 -> auPostcodeRepository.getPostcodeBriefByPrefix(number, POSTCODE_PICKER_LIST_LENGTH)
                    in 1000..9999 -> listOfNotNull(auPostcodeRepository.getPostcode(number))
                    else -> emptyList()
                }
            }
        } ?: emptyList()

    private fun isAccurateMatch(rawString: String, number: Long? = rawString.toLongOrNull()): Boolean =
        number?.let {
            when {
                rawString.startsWith("0") -> when (number) {
                    in 100..999 -> true
                    else -> false
                }
                else -> when (number) {
                    in 1000..9999 -> true
                    else -> false
                }
            }
        } ?: false

    companion object {
        private const val POSTCODE_PICKER_LIST_LENGTH = 10
        private const val POSTCODE_PICKER_LIST_RADIUS = POSTCODE_PICKER_LIST_LENGTH / 2
    }
}