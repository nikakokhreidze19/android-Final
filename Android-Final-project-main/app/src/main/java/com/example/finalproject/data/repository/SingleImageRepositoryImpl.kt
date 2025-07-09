package com.example.finalproject.data.repository

import com.example.finalproject.data.common.response_handler.HandleResponse
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.mapper.base.asResource
import com.example.finalproject.data.mapper.wallpaper_details.toDomain
import com.example.finalproject.data.service.WallpaperApiService
import com.example.finalproject.domain.model.GetImage
import com.example.finalproject.domain.repository.SingleImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SingleImageRepositoryImpl @Inject constructor(private val wallpaperApiService: WallpaperApiService, private val handleResponse: HandleResponse):
    SingleImageRepository {
    override suspend fun getImageById(apiKey: String, id: Long): Flow<Resource<GetImage>> {
        return handleResponse.safeApiCall {
            wallpaperApiService.getImageById(apiKey, id)
        }.asResource {
            it.hits.first().toDomain()
        }
    }
}