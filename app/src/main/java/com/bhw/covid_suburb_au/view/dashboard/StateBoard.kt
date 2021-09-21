package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.datasource.helper.CovidDisplayHelper
import com.bhw.covid_suburb_au.view.dashboard.viewobject.StateItemViewObject
import com.bhw.covid_suburb_au.view.theme.Red900

@Composable
fun StateBoard(data: StateItemViewObject) {
    Row {
        val iconRes = when {
            data.isMyState -> if (data.isHighlighted) R.drawable.ic_home_red_24 else R.drawable.ic_home_24
            else -> null
        }
        iconRes?.let {
            Image(
                painterResource(iconRes),
                stringResource(id = R.string.state_home_icon_content_description)
            )
        }
        Text(
            text = "${data.stateCode}: ${CovidDisplayHelper.casesToDisplay(data.cases)}",
            modifier = Modifier,
            color = if (data.isHighlighted) Red900 else MaterialTheme.colors.onPrimary
        )
    }
}

