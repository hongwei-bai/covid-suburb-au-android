package com.bhw.covid_suburb_au.view.main

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
                unselectedContentColor = MaterialTheme.colors.onPrimary,
                selectedContentColor = MaterialTheme.colors.primary,
                modifier = Modifier.background(MaterialTheme.colors.background),
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(items.indexOf(it))
                    }
                }
            )
        }
    }
}