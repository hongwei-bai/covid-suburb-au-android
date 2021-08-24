package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.view.component.LoadingContent
import com.bhw.covid_suburb_au.viewmodel.DashboardViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() },
        modifier = Modifier.fillMaxSize()
    ) {
        val data = viewModel.data.observeAsState().value

        data?.run {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Data version: ${data.lastUpdate}")
                Spacer(modifier = Modifier.size(40.dp))
                TopSuburbsBoard(
                    data = data.topSuburbs,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                )
            }
        } ?: LoadingContent()
    }
}

