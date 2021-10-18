package com.bhw.covid_suburb_au.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bhw.covid_suburb_au.R
import com.bhw.covid_suburb_au.dashboard.viewmodel.CasesByStateViewObject

@Composable
fun AusMapView(data: CasesByStateViewObject) {
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color.Black
    } else {
        Color.White
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(backgroundColor)
    ) {
        Image(
            painter = painterResource(R.drawable.map_au),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
        )

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.15f)
            ) {
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.12f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                StateBoard(data.nt)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.12f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StateBoard(data.wa)
                StateBoard(data.qld)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.40f)
                )
                StateBoard(
                    data = data.sa,
                    modifier = Modifier.weight(0.2f)
                )
                StateBoard(
                    data = data.nsw,
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.Start
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.05f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.6f)
                )
                StateBoard(
                    data.vic, modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )
                StateBoard(
                    data.act,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f),
                    horizontalArrangement = Arrangement.Start
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.6f)
                )
                StateBoard(
                    data.tas,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )
            }
        }
    }

}