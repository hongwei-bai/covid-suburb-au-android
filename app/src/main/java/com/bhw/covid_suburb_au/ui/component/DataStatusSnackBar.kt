package com.bhw.covid_suburb_au.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R

@Composable
fun DataStatusSnackBar(lastUpdate: String?) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
    ) {
        Text(
            text = lastUpdate?.let {
                stringResource(id = R.string.banner_last_update, it)
            } ?: stringResource(id = R.string.banner_last_update_never),
            style = MaterialTheme.typography.overline,
            color = MaterialTheme.colors.onPrimary
        )
    }
}