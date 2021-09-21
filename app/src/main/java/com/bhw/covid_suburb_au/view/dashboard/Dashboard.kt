package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        val lastUpdate = viewModel.lastUpdate.observeAsState().value
        val dataByState = viewModel.dataByState.observeAsState().value
        val dataBySuburbCompact = viewModel.suburbsDataCompact.observeAsState().value
        val dataBySuburbFull = viewModel.suburbsDataFull.observeAsState().value
        val isShowCompatList = remember { mutableStateOf(true) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            DataStatusSnackBar(null, lastUpdate)
            if (dataByState != null && dataBySuburbCompact != null && dataBySuburbFull != null) {
                StatesBoard(dataByState)
                SuburbsBoard(
                    data = if (isShowCompatList.value) dataBySuburbCompact else dataBySuburbFull,
                    isCompat = isShowCompatList.value
                ) {
                    isShowCompatList.value = !isShowCompatList.value
                }
            } else {
                LoadingContent(modifier = Modifier.size(240.dp))
            }
        }
    }
}

