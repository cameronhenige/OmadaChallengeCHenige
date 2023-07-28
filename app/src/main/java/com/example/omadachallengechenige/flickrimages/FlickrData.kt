package com.example.omadachallengechenige.flickrimages

import androidx.annotation.Keep


@Keep
data class FlickrPhotos(val photos: FlickrPhotoData)

@Keep
data class FlickrPhotoData(val page: Int, val pages: Int, val perpage: Int, val total: Int, val photo: List<FlickrPhoto>)

@Keep
data class FlickrPhoto(val id: String, val title: String, val server: String, val secret: String)

@Keep
data class Photo(val title: String, val photoUrl: String? = null)

fun FlickrPhotos.toPhotos(): List<Photo> {
    return photos.photo.map { it.toPhoto() }
}
fun FlickrPhoto.toPhoto(): Photo {
    return Photo(title, "https://live.staticflickr.com/${server}/${id}_${secret}.jpg")
}
