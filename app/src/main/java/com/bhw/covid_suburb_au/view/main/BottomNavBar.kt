package com.bhw.covid_suburb_au.view.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun BottomNavBar(pagerState: PagerState) {
    BottomNavigation {
        val coroutineScope = rememberCoroutineScope()
        val items = listOf(Screen.Dashboard, Screen.Map, Screen.Trends, Screen.Settings)
        items.forEach {
            BottomNavigationItem(
                icon = { Icon(it.icon, "") },
                selected = items.indexOf(it) == pagerState.currentPage,
                label = { Text(text = it.label) },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(items.indexOf(it))
                    }
                }
            )
        }
    }
}