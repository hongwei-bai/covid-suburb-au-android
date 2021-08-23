package com.bhw.covid_suburb_au.view.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.viewmodel.DashboardViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun Dashboard() {
    val viewModel = hiltViewModel<DashboardViewModel>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing.observeAsState().value == true),
        onRefresh = { viewModel.refresh() },
    ) {
        val rawData = viewModel.covidRawData.observeAsState().value

        Column {
            Text(text = "Data version:")
            if (rawData != null) {
                Text(text = rawData.lastUpdate)
            }

            Spacer(modifier = Modifier.size(40.dp))
            if (rawData != null) {
                val lastDayData = rawData.dataByDay.firstOrNull()
                if (lastDayData != null) {
                    Text(text = "New cases in Australia: ${lastDayData.caseTotal}")
                    Text(
                        text = "New cases in NSW: ${
                            lastDayData.caseByState.firstOrNull { it.stateCode == "NSW" }?.cases ?: 0
                        }"
                    )
                    Text(
                        text = "Top suburbs: ${
                            lastDayData.caseByPostcode.filterIndexed { index, _ -> index <= 2 }
                                .map { it.postcode }.joinToString(",")
                        }"
                    )

                    Spacer(modifier = Modifier.size(40.dp))
                    Text(
                        text = "Followed suburbs: ${
                            lastDayData.caseByPostcode.filter { suburb ->
                                listOf(2118, 2121, 2075).contains(suburb.postcode.toInt())
                            }.map { it.postcode }.joinToString(",")
                        }"
                    )
                }

            }
        }
    }
}

