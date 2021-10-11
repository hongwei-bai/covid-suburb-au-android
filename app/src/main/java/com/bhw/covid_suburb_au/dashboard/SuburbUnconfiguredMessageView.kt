package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R

@Composable
fun SuburbUnconfiguredMessageView() {
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