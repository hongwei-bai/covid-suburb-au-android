package com.bhw.covid_suburb_au.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoContentView(
    message: String,
    buttonText: String,
    refresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onError
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .padding(horizontal = 2.dp),
            onClick = { refresh.invoke() }
        ) {
            Text(
                text = buttonText,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.overline,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.onSecondary,
            )
        }
    }
}

