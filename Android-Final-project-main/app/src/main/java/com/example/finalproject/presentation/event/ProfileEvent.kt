package com.example.finalproject.presentation.event

sealed interface ProfileEvent {
    data object SignOutEvent : ProfileEvent
}