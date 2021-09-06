package com.bhw.covid_suburb_au.view.theme

import android.annotation.SuppressLint
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val CovidPrimary = Color(0XFF4b8d8d)
val CovidLight = Color(0xFFF5F5F5)
val CovidAccent = Color(0XFF8AC0C0)
val CovidDark = Color(0XA0000000)

@SuppressLint("ConflictingOnColor")
val CovidLightColors = lightColors(
    primary = CovidPrimary,
    primaryVariant = CovidPrimary,
    onPrimary = CovidDark,
    secondary = CovidAccent,
    secondaryVariant = CovidAccent,
    onSecondary = CovidDark,
    background = CovidLight,
    onBackground = CovidPrimary,
    surface = CovidLight,
    onSurface = CovidPrimary,
    error = CovidLight,
    onError = Red900
)

@SuppressLint("ConflictingOnColor")
val CovidDarkColors = lightColors(
    primary = CovidPrimary,
    primaryVariant = CovidPrimary,
    onPrimary = CovidLight,
    secondary = CovidAccent,
    secondaryVariant = CovidAccent,
    onSecondary = CovidLight,
    background = CovidPrimary,
    onBackground = CovidLight,
    surface = CovidPrimary,
    onSurface = CovidLight,
    error = Red900,
    onError = CovidLight
)
