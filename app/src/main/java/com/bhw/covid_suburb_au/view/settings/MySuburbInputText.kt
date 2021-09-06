package com.bhw.covid_suburb_au.view.settings

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.bhw.covid_suburb_au.view.settings.viewobject.SuburbNameViewObject

@Composable
fun MySuburbInputText(
    viewObject: SuburbNameViewObject?,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit
) {
    Log.d("bbbb", "compose MySuburbInputText: $viewObject")
    val list = viewObject?.suggestions
    val inputText = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    expanded.value = !list.isNullOrEmpty()

    Column {
        OutlinedTextField(
            value = inputText.value,
            onValueChange = {
                inputText.value = it
                onChange.invoke(it)
            },
            label = { Text("My suburb") },
            modifier = modifier
        )
        DropdownMenu(
            expanded = expanded.value,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            properties = PopupProperties(focusable = false),
            onDismissRequest = { expanded.value = false }) {
            list?.forEachIndexed { i, item ->
                if (i == 0) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else {
                    Text(text = item)
                }
            }
        }
    }
}