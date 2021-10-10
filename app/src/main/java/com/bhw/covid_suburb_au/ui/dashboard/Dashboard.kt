package com.bhw.covid_suburb_au.ui.dashboard

import androidx.compose.foundation.layout.*
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
import com.bhw.covid_suburb_au.ui.component.DataStatusSnackBar
import com.bhw.covid_suburb_au.ui.component.LoadingContent
import com.bhw.covid_suburb_au.ui.dashboard.viewobject.DashboardErrorState
import com.bhw.covid_suburb_au.ui.dashboard.viewobject.DashboardLoadingState
import com.bhw.covid_suburb_au.ui.dashboard.viewobject.DashboardSuccessState
import com.bhw.covid_suburb_au.ui.viewmodel.DashboardViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() }
    ) {
        val data = viewModel.dashboardBasicData.observeAsState().value
        val dataBySuburbCompact = viewModel.suburbsDataCompact.observeAsState().value
        val dataBySuburbFull = viewModel.suburbsDataFull.observeAsState().value
        val isShowCompatList = remember { mutableStateOf(true) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (data) {
                is DashboardLoadingState -> LoadingContent(modifier = Modifier.size(240.dp))
                is DashboardErrorState -> Text(
                    text = stringResource(R.string.snack_bar_generic_service_error),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 120.dp, start = 32.dp, end = 32.dp)
                )
                is DashboardSuccessState -> {
                    DataStatusSnackBar(data.lastUpdate)
                    StatesBoard(data.dataByState)
                    val suburbs = if (isShowCompatList.value) dataBySuburbCompact else dataBySuburbFull
                    if (suburbs?.list != null && suburbs.list.isNotEmpty()) {
                        SuburbsBoard(
                            data = suburbs,
                            isCompat = isShowCompatList.value
                        ) {
                            isShowCompatList.value = !isShowCompatList.value
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.dashboard_config_suburb_message1),
                            style = MaterialTheme.typography.overline,
                            color = MaterialTheme.colors.onSecondary,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(12.dp)
                        )
                        Text(
                            text = stringResource(R.string.dashboard_config_suburb_message2),
                            style = MaterialTheme.typography.overline,
                            color = MaterialTheme.colors.onSecondary,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(12.dp)
                        )
                        Text(
                            text = stringResource(R.string.dashboard_config_suburb_message3),
                            style = MaterialTheme.typography.overline,
                            color = MaterialTheme.colors.onSecondary,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}

