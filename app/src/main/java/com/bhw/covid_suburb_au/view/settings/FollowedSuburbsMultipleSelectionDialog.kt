package com.bhw.covid_suburb_au.view.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.viewmodel.SettingsViewModel

@Composable
fun FollowedSuburbsMultipleSelectionDialog(onSelect: (List<Long>) -> Unit) {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val myPostcode = viewModel.myPostcode.observeAsState().value
    val textTyping = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onSelect.invoke(emptyList()) }) {
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
                    },
                    label = { Text("Search") },
                )
            }
        }
    }
}