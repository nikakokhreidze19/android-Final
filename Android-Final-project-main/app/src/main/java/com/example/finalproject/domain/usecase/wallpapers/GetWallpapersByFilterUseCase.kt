package com.example.finalproject.domain.usecase.wallpapers

import androidx.paging.PagingData
import androidx.paging.map
import com.example.finalproject.presentation.mapper.toPresenter
import com.example.finalproject.domain.repository.WallpaperRepository
import com.example.finalproject.presentation.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWallpapersByFilterUseCase @Inject constructor(private val wallpaperRepository: WallpaperRepository) {
    suspend operator fun invoke(query: String, category: String) : Flow<PagingData<Image>> {
        return wallpaperRepository.getImagesByFilter(query, category).map {it.map { it.toPresenter() }}
    }
}