package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.dashboard.viewmodel.CasesByStateViewObject

@Composable
fun StatesBoard(data: CasesByStateViewObject) {
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 36.dp)
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StateBoard(data.wa)
        StateBoard(data.nt)
        StateBoard(data.sa)
        StateBoard(data.qld)
    }
    Row(
        modifier = Modifier
            .padding(start = 24.dp, end = 36.dp)
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StateBoard(data.nsw)
        StateBoard(data.vic)
        StateBoard(data.act)
        StateBoard(data.tas)
    }
}

