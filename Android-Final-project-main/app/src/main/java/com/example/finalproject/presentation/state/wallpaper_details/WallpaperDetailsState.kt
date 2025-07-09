package com.example.finalproject.presentation.state.wallpaper_details

import com.example.finalproject.presentation.model.Image

data class WallpaperDetailsState(
    val data: Image? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
