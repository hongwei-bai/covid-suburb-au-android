package com.bhw.covid_suburb_au.news

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R

@Composable
fun News() {
    Text(
        text = stringResource(id = R.string.coming_soon),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        textAlign = TextAlign.Center
    )
}

