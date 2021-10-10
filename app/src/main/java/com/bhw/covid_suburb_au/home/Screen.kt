package com.bhw.covid_suburb_au.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Outlined.Alarm)
    object Map : Screen("map", "Map", Icons.Outlined.Map)
    object Trends : Screen("trends", "Trends", Icons.Outlined.TrendingUp)
    object News : Screen("news", "News", Icons.Outlined.TrendingUp)
    object Settings : Screen("settings", "Settings", Icons.Outlined.Settings)
}