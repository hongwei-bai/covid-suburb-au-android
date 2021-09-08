package com.bhw.covid_suburb_au.view.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.viewmodel.SettingsViewModel

@Composable
fun FollowedSuburbsSetting() {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val followedSuburbs = viewModel.followedSuburbs.observeAsState().value
    val displayPickerDialog = remember { mutableStateOf(false) }

    val isNotEmpty = followedSuburbs?.isNullOrEmpty() == false

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.followed_suburbs),
            style = MaterialTheme.typography.h6
        )
        TextButton(
            onClick = { displayPickerDialog.value = true }
        ) {
            Text(
                text = stringResource(id = R.string.add),
                style = MaterialTheme.typography.overline,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isNotEmpty) {
            followedSuburbs?.forEach { (postcode, briefName) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = AuSuburbHelper.toDisplayString(postcode, briefName))
                    TextButton(
                        onClick = { }
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = MaterialTheme.typography.overline,
                            textDecoration = TextDecoration.Underline,
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
            }
        }
    }
}
