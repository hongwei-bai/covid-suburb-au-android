package com.bhw.covid_suburb_au.view.dashboard

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bhw.covid_suburb_au.view.dashboard.viewobject.TopSuburbViewObject

@Composable
fun TopSuburbsBoard(data: List<TopSuburbViewObject>, modifier: Modifier = Modifier) {
    data.forEach {
        Text(text = "${it.postcode}(${it.briefName}): ${it.cases}")
    }
}

