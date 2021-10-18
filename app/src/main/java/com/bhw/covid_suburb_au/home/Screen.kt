package com.bhw.covid_suburb_au.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val id: Int, val label: String, val icon: ImageVector) {
    object Dashboard : Screen(0, "Dashboard", Icons.Outlined.Alarm)
    object Map : Screen(1, "Map", Icons.Outlined.Map)
    object Trends : Screen(-1, "Trends", Icons.Outlined.TrendingUp)
    object News : Screen(2, "News", Icons.Outlined.TrendingUp)
    object Settings : Screen(3, "Settings", Icons.Outlined.Settings)
}