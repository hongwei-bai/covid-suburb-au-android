package com.bhw.covid_suburb_au.view.map

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.viewmodel.DashboardViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CovidSuburbMap() {
    val viewModel = hiltViewModel<DashboardViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() },
    ) {
        Text(text = "COVID-19 Suburbs Map")
    }
}

