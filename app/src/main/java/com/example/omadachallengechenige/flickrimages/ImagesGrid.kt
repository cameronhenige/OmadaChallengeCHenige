package com.example.omadachallengechenige.flickrimages

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.omadachallengechenige.navigation.Screen
import com.example.omadachallengechenige.ui.composables.ErrorCard
import com.example.omadachallengechenige.ui.composables.GridAsyncImage
import com.example.omadachallengechenige.ui.composables.GridProgressIndicator
import com.example.omadachallengechenige.ui.composables.SearchTextField

@Composable
fun ImagesGrid(
    navController: NavController,
    state: ImagesScreenState,
    onEvent: (SearchEvent) -> Unit
) {

    Scaffold(
        topBar = {
            SearchTextField(state = state, onEvent = onEvent)
        }) { padding ->

        LazyVerticalGrid(
            modifier = Modifier
                .padding(padding)
                .then(Modifier.padding(horizontal = 16.dp)),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.flickrPhotos.isNotEmpty()) {

                items(state.flickrPhotos.size) { i ->
                    val item = state.flickrPhotos[i]
                    if (i >= state.flickrPhotos.size - 1 && !state.isLoading) {
                        onEvent.invoke(SearchEvent.LoadNextItems)
                    }

                    GridAsyncImage(photoUrl = item.photoUrl) {
                        onEvent.invoke(SearchEvent.SelectedPhoto(i))
                        navController.navigate(Screen.DetailScreen.route)
                    }
                }
            } else {
                if (!state.isLoading) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            "No photos have been found.", textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                    }
                }
            }

            if (state.isLoading) {
                item {
                    GridProgressIndicator()
                }
            }

            state.error?.let {
                item {
                    ErrorCard { onEvent.invoke(SearchEvent.RetryFetch) }
                }
            }
        }
    }
}



sealed class SearchEvent {
    class SearchChanged(var query: String) : SearchEvent()
    class SelectedPhoto(var location: Int) : SearchEvent()

    object LoadNextItems : SearchEvent()
    object Search : SearchEvent()
    object RetryFetch : SearchEvent()
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImagesGridPreview() {
    AppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ImagesGrid(
                rememberNavController(),
                state = ImagesScreenState(
                    false, "Cats", listOf(
                        Photo(title = "First Article"),
                        Photo(title = "Second Article"),
                        Photo(title = "Third Article"),
                        Photo(title = "Fourth Article")
                    )
                )
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImagesGridIsLoadingPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ImagesGrid(
                rememberNavController(),
                state = ImagesScreenState(true, "Cats", emptyList(), -1, "An error")
            ) {}
        }
    }
}
