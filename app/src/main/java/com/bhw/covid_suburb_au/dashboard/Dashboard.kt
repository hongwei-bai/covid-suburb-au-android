package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
fun Dashboard(onGoToSettings: () -> Unit, onSuburbClicked: (Int) -> Unit) {
    val viewModel = hiltViewModel<DashboardViewModel>()
    val basicUiState = viewModel.basicUiState.observeAsState().value

    SwipeRefresh(
        state = rememberSwipeRefreshState(basicUiState is BasicUiState.Loading),
        onRefresh = { viewModel.refresh() }
    ) {
        val isPostcodeInitialised by viewModel.isPostcodeInitialised.observeAsState()
        val isShowCompatList = viewModel.isCompatList.observeAsState().value ?: true
        val suburbUiState = viewModel.suburbUiState.observeAsState().value
        val isSuburbConfigured by viewModel.isSuburbConfigured.observeAsState()
        val suburbLastUpdate by viewModel.suburbLastUpdate.observeAsState()

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
                    Spacer(modifier = Modifier.height(12.dp))
                    AusMapView(basicUiState.dataByState)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (suburbUiState?.isNotEmpty() == true) {
                        SuburbsBoard(
                            lastUpdate = suburbLastUpdate,
                            data = suburbUiState,
                            isCompat = isShowCompatList,
                            onSuburbClicked = onSuburbClicked
                        ) {
                            viewModel.query(!isShowCompatList)
                        }
                    }
                    if (isSuburbConfigured == false) {
                        SuburbUnConfiguredMessageView(onGoToSettings)
                    }
                }
            }
        }
    }
}