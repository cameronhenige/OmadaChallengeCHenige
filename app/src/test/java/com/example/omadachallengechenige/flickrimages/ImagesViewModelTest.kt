package com.example.omadachallengechenige.flickrimages

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImagesViewModelTest {

    private var fakeImagesServiceSuccess = FakeFlickerImagesServiceSuccess()
    private var fakeImagesServiceFailure = FakeFlickerImagesServiceFailure()
    private lateinit var viewModelSuccess: ImagesViewModel
    private lateinit var viewModelFailure: ImagesViewModel

    @Before
    fun setup() = runTest {
        viewModelSuccess = ImagesViewModel(FlickrRepository(fakeImagesServiceSuccess))
        viewModelFailure = ImagesViewModel(FlickrRepository(fakeImagesServiceFailure))
    }

    @Test
    fun `get images success`() {
        assertEquals(ImagesScreenState(false, "", listOf(), page = 1), viewModelSuccess.imagesState)
        viewModelSuccess.onSearchEvent(SearchEvent.Search)

        val listOfPhotos = listOf(Photo(title = "Image 1", photoUrl="https://live.staticflickr.com/Server 1/1_Secret 1.jpg"), Photo(title="Image 2", photoUrl="https://live.staticflickr.com/Server 2/2_Secret 2.jpg"))
        assertEquals(ImagesScreenState(false, "", listOfPhotos, page = 2), viewModelSuccess.imagesState)
    }

    @Test
    fun `get images failure`() {
        assertEquals(ImagesScreenState(false, "", listOf(), page = 1), viewModelFailure.imagesState)
        viewModelFailure.onSearchEvent(SearchEvent.Search)
        assertEquals(ImagesScreenState(isLoading = false, "", listOf(), error = "There was an error", page = 1), viewModelFailure.imagesState)
    }

    @Test
    fun `search changed`() {
        assertEquals(ImagesScreenState(false, "", listOf(), page = 1), viewModelFailure.imagesState)
        viewModelFailure.onSearchEvent(SearchEvent.SearchChanged("C"))
        assertEquals("C", viewModelFailure.imagesState.searchText)
        viewModelFailure.onSearchEvent(SearchEvent.SearchChanged("Ca"))
        assertEquals("Ca", viewModelFailure.imagesState.searchText)
        viewModelFailure.onSearchEvent(SearchEvent.SearchChanged("Cat"))
        assertEquals("Cat", viewModelFailure.imagesState.searchText)
    }

    @Test
    fun `load next items`() {
        viewModelSuccess.onSearchEvent(SearchEvent.Search)
        assertEquals(2, viewModelSuccess.imagesState.page)
        assertEquals(2, viewModelSuccess.imagesState.flickrPhotos.size)
        viewModelSuccess.onSearchEvent(SearchEvent.LoadNextItems)
        assertEquals(3, viewModelSuccess.imagesState.page)
        assertEquals(4, viewModelSuccess.imagesState.flickrPhotos.size)
        viewModelSuccess.onSearchEvent(SearchEvent.LoadNextItems)
        assertEquals(4, viewModelSuccess.imagesState.page)
        assertEquals(6, viewModelSuccess.imagesState.flickrPhotos.size)
    }

}