package com.example.finalproject.data.mapper.wallpapers

import com.example.finalproject.data.mapper.wallpaper_details.toDomain
import com.example.finalproject.data.model.WallpaperResponseDto
import com.example.finalproject.domain.model.GetWallpaperResponse

fun WallpaperResponseDto.toDomain(): GetWallpaperResponse {
    return GetWallpaperResponse(
        hits = hits.map { it.toDomain() }
    )
}