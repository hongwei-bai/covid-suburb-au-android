package com.bhw.covid_suburb_au.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.ui.viewmodel.SettingsViewModel

@Composable
fun SuburbPickerDialog(onSuburbPicked: (String?) -> Unit) {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val suburbSuggestions = viewModel.suburbSuggestions.observeAsState().value
    val textTyping = remember { mutableStateOf("") }
    val expandedStatus = remember { mutableStateOf(false) }
    val expanded = expandedStatus.value && !suburbSuggestions.isNullOrEmpty()

    Dialog(onDismissRequest = { onSuburbPicked.invoke(null) }) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 48.dp, bottom = 48.dp, start = 32.dp, end = 32.dp
                )
            ) {
                Text(text = stringResource(id = R.string.select_suburb))
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = textTyping.value,
                    onValueChange = {
                        textTyping.value = it
                        expandedStatus.value = true
                        viewModel.updateMySuburbPickerInput(it)
                    },
                    label = { Text("My suburb") },
                    modifier = Modifier.clickable {
                        expandedStatus.value = false
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    properties = PopupProperties(focusable = false),
                    onDismissRequest = { expandedStatus.value = false }) {
                    suburbSuggestions?.forEachIndexed { i, item ->
                        if (i == 0) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                                    .clickable {
                                        textTyping.value = item
                                        expandedStatus.value = false
                                        onSuburbPicked.invoke(item)
                                    }
                            )
                        } else {
                            Text(
                                text = item,
                                modifier = Modifier.clickable {
                                    textTyping.value = item
                                    expandedStatus.value = false
                                    onSuburbPicked.invoke(item)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}