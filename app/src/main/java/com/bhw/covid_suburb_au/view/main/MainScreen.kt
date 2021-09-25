package com.bhw.covid_suburb_au.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bhw.covid_suburb_au.view.dashboard.Dashboard
import com.bhw.covid_suburb_au.view.map.CovidSuburbMap
import com.bhw.covid_suburb_au.view.settings.Settings
import com.bhw.covid_suburb_au.view.trends.Trends
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        Screen.Dashboard,
        Screen.Map,
        Screen.Trends,
        Screen.Settings
    )
    val pagerState = rememberPagerState(pages.size)
    object : PagerListener {
        override fun onBackToDashboard() {
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }
    }
    Scaffold(bottomBar = { BottomNavBar(pagerState) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> Dashboard()
                    1 -> CovidSuburbMap()
                    2 -> Trends()
                    3 -> Settings()
                    else -> Unit
                }
            }
        }
    }
}

interface PagerListener {
    fun onBackToDashboard()
}