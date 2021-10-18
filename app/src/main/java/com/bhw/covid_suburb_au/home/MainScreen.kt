package com.bhw.covid_suburb_au.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.dashboard.Dashboard
import com.bhw.covid_suburb_au.map.CovidSuburbMap
import com.bhw.covid_suburb_au.map.MapViewModel
import com.bhw.covid_suburb_au.news.News
import com.bhw.covid_suburb_au.settings.Settings
import com.bhw.covid_suburb_au.util.PermissionState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(fineLocation: PermissionState) {
    val mapViewModel = hiltViewModel<MapViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        Screen.Dashboard,
        Screen.Map,
        Screen.News,
        Screen.Settings
    )
    val pagerState = rememberPagerState(pages.size)
    Scaffold(bottomBar = { BottomNavBar(pagerState) }) { innerPadding ->
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                if (page != Screen.Map.id) {
                    mapViewModel.clickedPostcode = null
                }
            }
        }
        Box(modifier = Modifier.padding(innerPadding)) {
            HorizontalPager(
                state = pagerState,
                dragEnabled = false
            ) { page ->
                when (page) {
                    Screen.Dashboard.id -> Dashboard { postcode ->
                        mapViewModel.clickedPostcode = postcode
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(Screen.Map.id)
                        }
                    }
                    Screen.Map.id -> CovidSuburbMap(fineLocation)
                    Screen.News.id -> News()
                    Screen.Settings.id -> Settings()
                    else -> Unit
                }
            }
        }
    }
}