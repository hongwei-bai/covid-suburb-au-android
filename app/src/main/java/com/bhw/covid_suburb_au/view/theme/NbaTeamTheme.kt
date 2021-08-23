package com.bhw.covid_suburb_au.view.theme

import androidx.compose.runtime.Composable
import com.bhw.covid_suburb_au.datasource.room.CovidAuEntity

@Composable
fun NbaTeamTheme(
    covidAu: CovidAuEntity?,
    content: @Composable () -> Unit
) {
    covidAu?.run {
        NbaTheme(
            colors = NBALightColors,
            content = content
        )
//        NbaTheme(
//            colors = lightColors(
//                primary = colorHome?.let { Color(it) } ?: NBALightColors.primary,
//                primaryVariant = colorHome?.let { Color(it) } ?: NBALightColors.primaryVariant,
//                onPrimary = colorLight?.let { Color(it) } ?: NBALightColors.onPrimary,
//                secondary = colorGuest?.let { Color(it) } ?: NBALightColors.secondary,
//                secondaryVariant = colorGuest?.let { Color(it) } ?: NBALightColors.secondaryVariant,
//                onSecondary = colorHome?.let { Color(it) } ?: NBALightColors.onSecondary,
//                background = colorLight?.let { Color(it) } ?: NBALightColors.background,
//                onBackground = colorHome?.let { Color(it) } ?: NBALightColors.onBackground
//            ),
//            content = content
//        )
    } ?: NbaTheme(
        colors = NBALightColors,
        content = content
    )
}