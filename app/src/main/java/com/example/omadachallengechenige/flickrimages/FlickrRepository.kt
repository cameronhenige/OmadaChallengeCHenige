package com.example.omadachallengechenige.flickrimages

class FlickrRepository(private var flickerImagesService: FlickerImagesService) {
    suspend fun getPhotosQuery(searchText: String, nextPage: Int): List<Photo> {
        return flickerImagesService.getPhotosFromQuery(searchText, nextPage).toPhotos()
    }

    suspend fun getRecentPhotos(nextPage: Int): List<Photo> {
        return flickerImagesService.getRecentPhotos(nextPage).toPhotos()
    }

}



