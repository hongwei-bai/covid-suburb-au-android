package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.data.helper.CovidDisplayHelper
import com.bhw.covid_suburb_au.dashboard.viewmodel.StateItemViewObject

@Composable
fun StateBoard(data: StateItemViewObject) {
    Row {
        val iconRes = if (data.isMyState) {
            if (data.isHighlighted) R.drawable.ic_home_red_24 else R.drawable.ic_home_24
        } else null
        iconRes?.let {
            Image(
                painterResource(iconRes),
                stringResource(id = R.string.state_home_icon_content_description)
            )
        }
        Text(
            text = "${data.stateCode}: ${CovidDisplayHelper.casesToDisplay(data.cases)}",
            modifier = Modifier,
            color = if (data.isHighlighted) MaterialTheme.colors.onError
            else MaterialTheme.colors.onPrimary
        )
    }
}

