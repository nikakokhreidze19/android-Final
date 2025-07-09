package com.example.finalproject.presentation.event

sealed interface SplashEvent {
    data object ReadSessionEvent : SplashEvent
}