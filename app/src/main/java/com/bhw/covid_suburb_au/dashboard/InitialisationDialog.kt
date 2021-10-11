package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.ui.component.LoadingContent

@Composable
fun InitialisationDialog() {
    Dialog(onDismissRequest = { }) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .padding(32.dp),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column {
                LoadingContent(
                    Modifier
                        .wrapContentWidth()
                        .heightIn(max = 200.dp)
                )
                Text(
                    text = stringResource(id = R.string.postcode_initialise_message),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}