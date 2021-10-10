package com.bhw.covid_suburb_au.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.ui.viewmodel.SettingsViewModel
import timber.log.Timber

@Composable
fun FollowedSuburbsPickerDialog(onDismiss: () -> Unit) {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val suburbs = viewModel.adjacentSuburbs.observeAsState().value
    val textTyping = remember { mutableStateOf("") }

    Timber.d("state(suburbs): ${suburbs?.size}")
    if (suburbs?.size == 1) {
        Timber.d("${suburbs.first().postcode}")
    }

    Dialog(onDismissRequest = { onDismiss.invoke() }) {
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
                TextField(
                    value = textTyping.value,
                    onValueChange = {
                        textTyping.value = it
                        viewModel.updateFollowedSuburbsPickerInput(it)
                    },
                    label = { Text("Search") },
                )

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    suburbs?.forEach { item ->
                        Row(
                            modifier = Modifier.clickable {
                                if (item.isSelectable) {
                                    viewModel.setFollowPostcode(item.postcode, !item.isSelected)
                                }
                            }
                        ) {
                            Checkbox(
                                checked = item.isSelected || !item.isSelectable,
                                enabled = item.isSelectable,
                                onCheckedChange = {
                                    viewModel.setFollowPostcode(item.postcode, it)
                                }
                            )
                            Text(text = item.display)
                        }
                    }
                }
            }
        }
    }
}