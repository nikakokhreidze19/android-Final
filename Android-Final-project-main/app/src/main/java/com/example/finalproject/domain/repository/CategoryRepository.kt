package com.example.finalproject.domain.repository

import com.example.finalproject.presentation.model.category.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}