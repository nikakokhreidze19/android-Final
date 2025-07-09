package com.example.finalproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.finalproject.BuildConfig
import com.example.finalproject.data.paging.WallpaperPagingSource
import com.example.finalproject.data.service.WallpaperApiService
import com.example.finalproject.domain.model.GetImage
import com.example.finalproject.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(private val apiService: WallpaperApiService) :
    WallpaperRepository {
    override suspend fun getImagesByFilter(query: String, category: String): Flow<PagingData<GetImage>> =
        Pager(PagingConfig(pageSize = BuildConfig.PAGE_SIZE)) {
            WallpaperPagingSource(apiService, BuildConfig.API_KEY, query, category)
        }.flow

    override suspend fun getImages(): Flow<PagingData<GetImage>> =
        Pager(PagingConfig(pageSize = BuildConfig.PAGE_SIZE)) {
            WallpaperPagingSource(apiService, BuildConfig.API_KEY, "", "all")
        }.flow
}