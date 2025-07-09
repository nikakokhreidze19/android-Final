package com.example.finalproject.presentation.mapper

import com.example.finalproject.domain.model.GetImage
import com.example.finalproject.presentation.model.Image

fun GetImage.toPresenter(): Image = Image(
    id, pageURL, tags, previewURL, webformatURL, largeImageURL, views, downloads, collections, likes, comments
)