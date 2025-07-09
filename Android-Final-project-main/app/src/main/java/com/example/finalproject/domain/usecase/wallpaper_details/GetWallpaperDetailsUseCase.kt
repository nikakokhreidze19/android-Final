package com.example.finalproject.domain.usecase.wallpaper_details

import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.mapper.base.asResource
import com.example.finalproject.presentation.mapper.toPresenter
import com.example.finalproject.domain.repository.SingleImageRepository
import com.example.finalproject.presentation.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWallpaperDetailsUseCase @Inject constructor(private val wallpaperDetailsRepository: SingleImageRepository) {
    suspend operator fun invoke(apiKey: String, id: Long): Flow<Resource<Image>> {
        return wallpaperDetailsRepository.getImageById(apiKey, id).asResource { it.toPresenter() }
    }
}