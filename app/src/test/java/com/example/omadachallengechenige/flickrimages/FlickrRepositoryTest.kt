package com.example.omadachallengechenige.flickrimages

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlickrRepositoryTest {

    private var fakeImagesServiceSuccess = FakeFlickerImagesServiceSuccess()
    private lateinit var flickrRepository: FlickrRepository

    @Before
    fun setup() = runTest {
        flickrRepository = FlickrRepository(fakeImagesServiceSuccess)
    }

    @Test
    fun `query photos success`() = runTest {
        val response = flickrRepository.getPhotosQuery("test", 1)
        Assert.assertEquals(listOf(Photo(title="Image 1", photoUrl="https://live.staticflickr.com/Server 1/1_Secret 1.jpg"), Photo(title="Image 2", photoUrl="https://live.staticflickr.com/Server 2/2_Secret 2.jpg")),response)
    }

    @Test
    fun `recent photos success`() = runTest {
        val response = flickrRepository.getRecentPhotos(1)
        Assert.assertEquals(listOf(Photo(title="Image 1", photoUrl="https://live.staticflickr.com/Server 1/1_Secret 1.jpg"), Photo(title="Image 2", photoUrl="https://live.staticflickr.com/Server 2/2_Secret 2.jpg")),response)
    }

}