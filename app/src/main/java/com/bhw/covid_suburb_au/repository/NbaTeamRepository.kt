package com.bhw.covid_suburb_au.repository

import com.bhw.covid_suburb_au.datasource.network.service.NbaThemeService
import com.bhw.covid_suburb_au.datasource.room.TeamThemeDao
import com.bhw.covid_suburb_au.datasource.room.TeamThemeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NbaTeamRepository @Inject constructor(
    private val nbaThemeService: NbaThemeService,
    private val teamThemeDao: TeamThemeDao
) {
    suspend fun fetchTeamThemeFromBackend(team: String) {
        if (DEBUG_TEAM_THEME_ON_LOCAL) {
            val localTheme = localTeamTheme.getTeamTheme(team)
            teamThemeDao.save(localTheme)
        } else {
            val response = nbaThemeService.getTeamTheme(team, -1)
            val data = response.body()
            if (response.isSuccessful && data != null) {
                teamThemeDao.save(data.map())
            }
        }
    }

    fun getTeamTheme(team: String): Flow<TeamThemeEntity> {
        return teamThemeDao.getTeamTheme().onEach {
            it ?: fetchTeamThemeFromBackend(team)
        }.filterNotNull()
    }
}