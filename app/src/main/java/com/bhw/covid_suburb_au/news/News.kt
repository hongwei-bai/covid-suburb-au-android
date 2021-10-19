package com.bhw.covid_suburb_au.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.util.OpenExternalLinkHelper


@Composable
fun News() {
    val viewModel = hiltViewModel<NewsViewModel>()

    val title by viewModel.title.observeAsState()
    val news by viewModel.news.observeAsState()
    val link by viewModel.link.observeAsState()
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.news_title),
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.news_link_message, title ?: stringResource(R.string.default_link_name)),
            style = MaterialTheme.typography.overline,
            color = MaterialTheme.colors.onSecondary,
            textDecoration = if (link != null) {
                TextDecoration.Underline
            } else {
                TextDecoration.None
            },
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    OpenExternalLinkHelper.openLink(context, link)
                }
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = news ?: stringResource(R.string.no_news_message),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

