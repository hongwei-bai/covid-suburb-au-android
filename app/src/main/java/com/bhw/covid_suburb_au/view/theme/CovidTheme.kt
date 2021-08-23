package com.bhw.covid_suburb_au.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CovidTheme(
    colors: Colors? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = colors ?: if (darkTheme) CovidDarkColors else CovidLightColors,
        typography = CovidTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}