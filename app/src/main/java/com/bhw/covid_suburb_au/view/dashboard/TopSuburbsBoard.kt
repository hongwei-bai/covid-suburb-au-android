package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.view.dashboard.viewobject.TopSuburbViewObject

@Composable
fun TopSuburbsBoard(data: List<TopSuburbViewObject>, modifier: Modifier = Modifier) {
    data.forEach {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.map_pin2))
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 24.dp)
                .height(36.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${it.briefName}(${it.postcode}): +${it.cases} cases",
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
    val compositionArrow by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.arrow_down))
    Row(modifier = Modifier.height(64.dp)) {
        LottieAnimation(
            compositionArrow,
            speed = 1f,
            contentScale = ContentScale.FillHeight,
            iterations = LottieConstants.IterateForever
        )
    }
}

