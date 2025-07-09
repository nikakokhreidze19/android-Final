package com.example.finalproject.presentation.event

sealed interface WallpapersEvent {
    data class FilterByCategoryEvent(val category: String) : WallpapersEvent
    data class FilterByQueryEvent(val query: String) : WallpapersEvent
    data object FetchDefaultDataEvent : WallpapersEvent
}