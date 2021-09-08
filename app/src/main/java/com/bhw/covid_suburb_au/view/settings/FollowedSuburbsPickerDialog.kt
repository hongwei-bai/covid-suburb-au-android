package com.bhw.covid_suburb_au.view.settings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bhw.covid_suburb_au.datasource.helper.AuSuburbHelper
import com.bhw.covid_suburb_au.viewmodel.SettingsViewModel

@Composable
fun FollowedSuburbsPickerDialog(onSelect: (List<Long>) -> Unit) {
    val viewModel = hiltViewModel<SettingsViewModel>()

    val myPostcode = viewModel.myPostcode.observeAsState().value
    val suburbs = viewModel.suburbs.collectAsLazyPagingItems()
    val textTyping = remember { mutableStateOf("") }

    Log.d("bbbb", "allSuburbs: ${suburbs.loadState}")

    Dialog(onDismissRequest = { onSelect.invoke(emptyList()) }) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 48.dp, bottom = 48.dp, start = 32.dp, end = 32.dp
                )
            ) {
                TextField(
                    value = textTyping.value,
                    onValueChange = {
                        textTyping.value = it
                    },
                    label = { Text("Search") },
                )
                LazyColumn(modifier = Modifier.wrapContentHeight()) {
                    items(suburbs.itemCount) { index ->
                        suburbs[index]?.let {
                            AuSuburbHelper.toDisplayString(it.postcode, it.suburbs)?.let { display ->
                                Text(text = display)
                            }
                        }
                    }

                    suburbs.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingItem() }
                                item { LoadingItem() }
                            }
                            loadState.append is LoadState.Loading -> {
                                item { LoadingItem() }
                                item { LoadingItem() }
                            }
                            loadState.refresh is LoadState.Error -> {
                                Log.d("bbbb", "handlePaginationDataError")
                            }
                            loadState.append is LoadState.Error -> {
                                Log.d("bbbb", "handlePaginationAppendError")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .testTag("ProgressBarItem")
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}