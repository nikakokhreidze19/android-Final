package com.example.finalproject.data.mapper.wallpaper_details

import com.example.finalproject.data.model.ImageDto
import com.example.finalproject.domain.model.GetImage

fun ImageDto.toDomain(): GetImage {
    return GetImage(
        id, pageURL, tags, previewURL, webformatURL, largeImageURL, views, downloads, collections, likes, comments
    )
}