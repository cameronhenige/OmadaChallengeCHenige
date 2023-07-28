package com.example.omadachallengechenige.flickrimages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omadachallengechenige.ui.util.FlickrPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private var flickrRepository: FlickrRepository) :
    ViewModel() {

    var imagesState by mutableStateOf(ImagesScreenState())
        private set

    private val paginator = FlickrPaginator(initialKey = imagesState.page,
        onLoadUpdated = {
            imagesState = imagesState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            if (imagesState.searchText.isEmpty()) {
                flickrRepository.getRecentPhotos(nextPage)
            } else {
                flickrRepository.getPhotosQuery(imagesState.searchText, nextPage)
            }
        },
        getNextKey = {
            imagesState.page + 1
        },
        onError = {
            imagesState = imagesState.copy(isLoading = false, error = "There was an error")
        },
        onSuccess = { items, newKey ->
            imagesState =
                imagesState.copy(flickrPhotos = imagesState.flickrPhotos + items, page = newKey)

        })

    private fun loadNextItems(forceFetch: Boolean) {
        viewModelScope.launch {
            paginator.loadNextItems(forceFetch)
        }
    }

    fun onSearchEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.SearchChanged -> imagesState =
                imagesState.copy(searchText = searchEvent.query)

            SearchEvent.Search -> {
                paginator.reset()
                imagesState = imagesState.copy(flickrPhotos = emptyList(), error = null, page = 1)
                loadNextItems(true)
            }

            SearchEvent.LoadNextItems -> loadNextItems(false)
            SearchEvent.RetryFetch -> {
                imagesState = imagesState.copy(error = null)
                loadNextItems(true)
            }

            is SearchEvent.SelectedPhoto -> {
                imagesState = imagesState.copy(selectedPhoto = searchEvent.location)
            }
        }
    }

}

data class ImagesScreenState(
    val isLoading: Boolean = false,
    val searchText: String = "",
    val flickrPhotos: List<Photo> = emptyList(),
    val selectedPhoto: Int? = null,
    val error: String? = null,
    val page: Int = 1
)