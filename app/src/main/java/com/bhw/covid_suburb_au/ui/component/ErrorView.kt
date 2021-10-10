package com.bhw.covid_suburb_au.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bhw.covid_suburb_au.R

@Composable
fun ErrorView(
    message: String? = null,
    refresh: () -> Unit
) {
    NoContentView(
        message = message ?: stringResource(id = R.string.generic_error),
        buttonText = stringResource(id = R.string.retry),
        refresh = refresh
    )
}
