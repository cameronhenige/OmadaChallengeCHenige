package com.example.omadachallengechenige.flickrimages

import javax.inject.Inject

class FakeFlickerImagesServiceSuccess @Inject constructor(): FlickerImagesService {

    private val fakeFlickrPhotos = FlickrPhotos(
        FlickrPhotoData(1,5, 20, 500,listOf(
            FlickrPhoto(id= "1", title = "Image 1", server = "Server 1", secret = "Secret 1"),
            FlickrPhoto(id= "2", title = "Image 2", server = "Server 2", secret = "Secret 2")
    ) )
    )
    override suspend fun getRecentPhotos(page: Int, perPage: Int): FlickrPhotos {
        return fakeFlickrPhotos
    }

    override suspend fun getPhotosFromQuery(
        searchText: String,
        page: Int,
        perPage: Int
    ): FlickrPhotos {
        return fakeFlickrPhotos
    }
}