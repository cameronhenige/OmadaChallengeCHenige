package com.example.omadachallengechenige.flickrimages

import javax.inject.Inject

class FakeFlickerImagesServiceFailure @Inject constructor(): FlickerImagesService {
    override suspend fun getRecentPhotos(page: Int, perPage: Int): FlickrPhotos {
        throw RuntimeException("Error getting photos")
    }

    override suspend fun getPhotosFromQuery(
        searchText: String,
        page: Int,
        perPage: Int
    ): FlickrPhotos {
        throw RuntimeException("Error getting photos")
    }
}