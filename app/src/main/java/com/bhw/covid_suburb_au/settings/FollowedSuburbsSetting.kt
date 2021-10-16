package com.bhw.covid_suburb_au.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.data.helper.AuSuburbHelper

@Composable
fun FollowedSuburbsSetting() {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val followedSuburbs by viewModel.followedSuburbs.observeAsState()
    val displayPickerDialog = remember { mutableStateOf(false) }
    val displayConfirmationDialog = remember { mutableStateOf(false) }
    val isNotEmpty = followedSuburbs?.isNullOrEmpty() == false

    val pendingUnfollowPostcode: MutableState<Int?> = remember { mutableStateOf(null) }
    val pendingUnfollowSuburb: MutableState<String?> = remember { mutableStateOf(null) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.followed_suburbs),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
        )
        TextButton(
            onClick = { displayPickerDialog.value = true }
        ) {
            Text(
                text = stringResource(id = R.string.add),
                style = MaterialTheme.typography.overline,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 24.dp)
    ) {
        if (isNotEmpty) {
            followedSuburbs?.forEach { (postcode, briefName) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = AuSuburbHelper.toDisplayString(postcode, briefName),
                        color = MaterialTheme.colors.onPrimary,
                    )
                    TextButton(
                        onClick = {
                            pendingUnfollowPostcode.value = postcode
                            pendingUnfollowSuburb.value = AuSuburbHelper.toDisplayString(postcode, briefName)
                            displayConfirmationDialog.value = true
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.unfollow),
                            style = MaterialTheme.typography.overline,
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colors.onSecondary,
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
            }
        } else {
            Text(text = stringResource(R.string.configure_your_followed_suburbs))
        }
        if (displayPickerDialog.value) {
            FollowedSuburbsPickerDialog {
                displayPickerDialog.value = false
                viewModel.updateFollowedSuburbsPickerInput("")
            }
        }
        if (displayConfirmationDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    displayConfirmationDialog.value = false
                    pendingUnfollowPostcode.value = null
                    pendingUnfollowSuburb.value = null
                },
                title = { Text(text = stringResource(id = R.string.unfollow_suburb_confirmation_dialog_title)) },
                text = {
                    Text(
                        text = stringResource(
                            id = R.string.unfollow_suburb_confirmation_dialog_message,
                            pendingUnfollowSuburb.value ?: ""
                        )
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        pendingUnfollowPostcode.value?.let { postcode ->
                            viewModel.setFollowPostcode(postcode, false)
                        }
                        pendingUnfollowPostcode.value = null
                        pendingUnfollowSuburb.value = null
                        displayConfirmationDialog.value = false
                    }) {
                        Text(text = stringResource(id = R.string.unfollow))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        displayConfirmationDialog.value = false
                        pendingUnfollowPostcode.value = null
                        pendingUnfollowSuburb.value = null
                    }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                }
            )
        }
    }
}
