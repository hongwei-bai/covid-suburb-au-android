package com.bhw.covid_suburb_au.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DataStatusSnackBar(dataStatus: DataStatus?, lastUpdate: String?) {
    val message: String? = when (dataStatus) {
        is DataStatus.DataIsUpToDate -> stringResource(id = R.string.snack_bar_data_up_to_date)
        is DataStatus.ServiceError -> dataStatus.message
            ?: stringResource(id = R.string.snack_bar_service_error)
        else -> null
    }
    val isHighlightState = remember { mutableStateOf(message != null) }
    val coroutineScope = rememberCoroutineScope()
    if (isHighlightState.value && message != null) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.error)
                .padding(8.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onError
            )
            coroutineScope.launch {
                delay(3000)
                isHighlightState.value = false
            }
        }
    } else {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
        ) {
            Text(
                text = lastUpdate?.let {
                    stringResource(id = R.string.snack_bar_last_update, it)
                } ?: stringResource(id = R.string.snack_bar_last_update_never),
                style = MaterialTheme.typography.overline,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

sealed class DataStatus {
    data class ServiceError(val message: String? = null) : DataStatus()
    object DataIsUpToDate : DataStatus()
}