package com.bhw.covid_suburb_au.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val CovidPrimary = Color(0XFF4b8d8d)
val CovidLight = Color(0xFFF5F5F5)
val CovidAccent = Color(0XFF8AC0C0)
val CovidDark = Color(0XFF202020)

@SuppressLint("ConflictingOnColor")
val CovidLightColors = lightColors(
    primary = CovidPrimary,
    primaryVariant = CovidPrimary,
    onPrimary = CovidDark,
    secondary = CovidAccent,
    secondaryVariant = CovidAccent,
    onSecondary = CovidPrimary,
    background = CovidLight,
    onBackground = CovidPrimary,
    surface = CovidLight,
    onSurface = CovidPrimary,
    error = CovidLight,
    onError = Red900
)

@SuppressLint("ConflictingOnColor")
val CovidDarkColors = darkColors(
    primary = CovidAccent,
    primaryVariant = CovidAccent,
    onPrimary = CovidLight,
    secondary = CovidPrimary,
    secondaryVariant = CovidPrimary,
    onSecondary = CovidAccent,
    background = CovidDark,
    onBackground = CovidLight,
    surface = CovidDark,
    onSurface = CovidLight,
    error = CovidDark,
    onError = Red500
)
