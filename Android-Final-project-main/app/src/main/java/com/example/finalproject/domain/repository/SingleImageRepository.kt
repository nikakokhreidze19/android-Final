package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.GetImage
import kotlinx.coroutines.flow.Flow

interface SingleImageRepository {
    suspend fun getImageById(apiKey: String, id: Long): Flow<Resource<GetImage>>
}