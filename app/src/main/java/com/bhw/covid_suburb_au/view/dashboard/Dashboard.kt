package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.view.component.DataStatusSnackBar
import com.bhw.covid_suburb_au.view.component.LoadingContent
import com.bhw.covid_suburb_au.viewmodel.DashboardViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() }
    ) {
        val data = viewModel.data.observeAsState().value
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DataStatusSnackBar(null, data?.lastUpdate)
            data?.run {
                TopSuburbsBoard(
                    data = data.topSuburbs
                )
            } ?: LoadingContent()
        }
    }
}

