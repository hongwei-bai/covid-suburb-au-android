package com.bhw.covid_suburb_au.view.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bhw.covid_suburb_au.view.dashboard.Dashboard
import com.google.accompanist.pager.ExperimentalPagerApi
import com.bhw.covid_suburb_au.view.navigation.BottomNavBar


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavBar(navController) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavCompose(navController)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun MainNavCompose(navController: NavHostController) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            Dashboard()
        }
//        composable("season") {
//            Season()
//        }
//        composable("goal") {
//            Goal()
//        }
//        composable("settings") {
//            Settings()
//        }
    }
}