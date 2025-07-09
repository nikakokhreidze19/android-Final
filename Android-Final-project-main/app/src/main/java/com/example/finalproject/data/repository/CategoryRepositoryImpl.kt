package com.example.finalproject.data.repository

import com.example.finalproject.presentation.model.category.Categories
import com.example.finalproject.presentation.model.category.Category
import com.example.finalproject.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return Categories.entries.map { category ->
            Category(categories = category)
        }
    }
}