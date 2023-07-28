package com.example.omadachallengechenige.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.omadachallengechenige.flickrimages.ImagesScreenState
import com.example.omadachallengechenige.flickrimages.SearchEvent

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SearchTextField(
    state: ImagesScreenState,
    onEvent: (SearchEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search Flickr") },
        value = state.searchText,
        onValueChange = { onEvent.invoke(SearchEvent.SearchChanged(it)) },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onEvent.invoke(SearchEvent.Search)
            }
        ), trailingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                onEvent.invoke(SearchEvent.Search)
            }) {
                Icon(Icons.Filled.Search, "Search")
            }

        })
}