package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
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
        val dataStatus = viewModel.dataStatus.observeAsState().value
        val lastUpdate = viewModel.lastUpdate.observeAsState().value
        val dataByState = viewModel.dataByState.observeAsState().value
        val dataBySuburbCompact = viewModel.suburbsDataCompact.observeAsState().value
        val dataBySuburbFull = viewModel.suburbsDataFull.observeAsState().value
        val isShowCompatList = remember { mutableStateOf(true) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            DataStatusSnackBar(dataStatus, lastUpdate)
            if (dataByState != null && dataBySuburbCompact != null && dataBySuburbFull != null) {
                StatesBoard(dataByState)
                SuburbsBoard(
                    data = if (isShowCompatList.value) dataBySuburbCompact else dataBySuburbFull,
                    isCompat = isShowCompatList.value
                ) {
                    isShowCompatList.value = !isShowCompatList.value
                }
            } else if (dataStatus != null) {
                Text(
                    text = stringResource(R.string.snack_bar_generic_service_error),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 120.dp, start = 32.dp, end = 32.dp)
                )
            } else {
                LoadingContent(modifier = Modifier.size(240.dp))
            }
        }
    }
}

