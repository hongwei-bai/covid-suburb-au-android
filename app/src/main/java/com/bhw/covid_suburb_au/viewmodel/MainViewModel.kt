package com.bhw.covid_suburb_au.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bhw.covid_suburb_au.datasource.local.AppSettings
import com.bhw.covid_suburb_au.datasource.room.TeamThemeEntity
import com.bhw.covid_suburb_au.repository.NbaTeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    nbaTeamRepository: NbaTeamRepository
) : ViewModel() {
    val teamTheme: LiveData<TeamThemeEntity> =
        nbaTeamRepository.getTeamTheme(AppSettings.myTeam)
            .asLiveData(viewModelScope.coroutineContext)
}