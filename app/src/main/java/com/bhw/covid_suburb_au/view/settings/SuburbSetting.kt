package com.bhw.covid_suburb_au.view.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.viewmodel.SettingsViewModel

@Composable
fun SuburbSetting() {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val mySuburb = viewModel.suburb.observeAsState().value
    val displayPickerDialog = remember { mutableStateOf(false) }
    val displayConfirmationDialog = remember { mutableStateOf(false) }

    val newSuburb: MutableState<String?> = remember { mutableStateOf(null) }

    Text(
        text = stringResource(R.string.current_suburb),
        style = MaterialTheme.typography.h6
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        mySuburb?.let {
            Text(text = mySuburb)
        } ?: Text(text = stringResource(R.string.configure_your_suburb))
        TextButton(
            onClick = { displayPickerDialog.value = true }
        ) {
            Text(
                text = stringResource(id = R.string.change),
                style = MaterialTheme.typography.overline,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.wrapContentWidth()
            )
        }
        if (displayPickerDialog.value) {
            SuburbPickerDialog {
                displayPickerDialog.value = false
                newSuburb.value = it
                mySuburb?.let {
                    displayConfirmationDialog.value = true
                } ?: newSuburb.value?.let { newSuburbValue ->
                    viewModel.setMySuburb(newSuburbValue)
                }
            }
        }
        if (displayConfirmationDialog.value) {
            mySuburb?.let {
                newSuburb.value?.let { newSuburbValue ->
                    AlertDialog(
                        onDismissRequest = { displayConfirmationDialog.value = false },
                        title = { Text(text = stringResource(id = R.string.replace_suburb_title)) },
                        text = { Text(text = stringResource(id = R.string.replace_suburb_message, mySuburb, newSuburbValue)) },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.setMySuburb(newSuburbValue)
                                displayConfirmationDialog.value = false
                            }) {
                                Text(text = stringResource(id = R.string.update))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { displayConfirmationDialog.value = false }) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                        }
                    )
                }
            }
        }
    }
}
