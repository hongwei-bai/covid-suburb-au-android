package com.bhw.covid_suburb_au.view.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.bhw.covid_suburb_au.view.main.Screen

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val items = listOf(Screen.Dashboard, Screen.Map, Screen.Trends, Screen.Settings)
        items.forEach {
            BottomNavigationItem(
                icon = { Icon(it.icon, "") },
                selected = currentRoute == it.route,
                label = { Text(text = it.label) },
                onClick = {
                    navController.popBackStack(
                        navController.graph.startDestinationId, false
                    )
                    if (currentRoute != it.route) {
                        if (it.route == "dashboard") {
                            navController.navigate(it.route) {
                                popUpTo("dashboard") { inclusive = true }
                                launchSingleTop = true
                            }
                        } else {
                            navController.navigate(it.route)
                        }
                    }
                }
            )
        }
    }
}