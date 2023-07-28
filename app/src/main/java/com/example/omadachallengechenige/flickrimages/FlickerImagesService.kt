package com.example.omadachallengechenige.flickrimages

import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerImagesService {

    @GET("/services/rest/?method=flickr.photos.getRecent")
    suspend fun getRecentPhotos(
        @Query("page") page: Int,
    ): FlickrPhotos

    @GET("/services/rest/?method=flickr.photos.search")
    suspend fun getPhotosFromQuery(
        @Query("text") searchText: String,
        @Query("page") page: Int,
    ): FlickrPhotos

}