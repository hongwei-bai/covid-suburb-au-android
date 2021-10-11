package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.dashboard.viewmodel.BasicUiState
import com.bhw.covid_suburb_au.dashboard.viewmodel.DashboardViewModel
import com.bhw.covid_suburb_au.ui.component.DataStatusSnackBar
import com.bhw.covid_suburb_au.ui.component.ErrorView
import com.bhw.covid_suburb_au.ui.component.LoadingContent
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val basicUiState = viewModel.basicUiState.observeAsState().value

    SwipeRefresh(
        state = rememberSwipeRefreshState(basicUiState is BasicUiState.Loading),
        onRefresh = { viewModel.refresh() }
    ) {
        val isPostcodeInitialised = viewModel.isPostcodeInitialised.observeAsState().value
        val isShowCompatList = remember { mutableStateOf(true) }
        val suburbUiState = viewModel.suburbUiState.observeAsState().value
        val isSuburbConfigured = viewModel.isSuburbConfigured.observeAsState().value ?: true

        if (isPostcodeInitialised == false) {
            InitialisationDialog()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            when (basicUiState) {
                is BasicUiState.Loading -> LoadingContent()
                is BasicUiState.Error -> ErrorView { viewModel.refresh() }
                is BasicUiState.Success -> {
                    DataStatusSnackBar(basicUiState.lastUpdate)
                    Spacer(modifier = Modifier.height(16.dp))
                    StatesBoard(basicUiState.dataByState)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (suburbUiState?.isNotEmpty() == true) {
                        SuburbsBoard(
                            data = suburbUiState,
                            isCompat = isShowCompatList.value
                        ) {
                            isShowCompatList.value = !isShowCompatList.value
                        }
                    }
                    if (!isSuburbConfigured) {
                        SuburbUnconfiguredMessageView()
                    }
                }
            }
        }
    }
}