package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.datasource.helper.CovidDisplayHelper
import com.bhw.covid_suburb_au.view.dashboard.viewobject.CaseBySuburbViewObject
import com.bhw.covid_suburb_au.view.theme.Red900

@Composable
fun SuburbsBoard(data: CaseBySuburbViewObject, isCompat: Boolean, onExpandButtonClicked: () -> Unit) {
    LazyColumn {
        items(data.list) {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.map_pin2))
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 24.dp)
                    .height(36.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val iconRes = when {
                    it.isMySuburb -> if (it.isHighlighted) R.drawable.ic_home_red_24 else R.drawable.ic_home_24
                    it.isFollowed -> if (it.isHighlighted) R.drawable.ic_eye_red_24 else R.drawable.ic_eye_24
                    else -> null
                }
                iconRes?.let {
                    Image(
                        painterResource(iconRes),
                        stringResource(id = R.string.home_icon_content_description)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }

                Text(
                    text = "${it.postcode} ${it.briefName}: ${CovidDisplayHelper.casesToDisplay(it.cases)}",
                    color = if (it.isHighlighted) Red900 else MaterialTheme.colors.onPrimary,
                    fontWeight = if (it.isMySuburb) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
                Spacer(modifier = Modifier.fillMaxWidth())
                LottieAnimation(
                    composition,
                    speed = 1f,
                    contentScale = ContentScale.FillHeight,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }

    Row(modifier = Modifier.height(64.dp)) {
        TextButton(
            onClick = { onExpandButtonClicked.invoke() }
        ) {
            Text(
                text = stringResource(
                    id = if (isCompat) R.string.click_to_see_full_suburb_list
                    else R.string.click_to_show_compat_list
                ),
                style = MaterialTheme.typography.overline,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

