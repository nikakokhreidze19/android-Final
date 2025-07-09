package com.example.finalproject.presentation.event

sealed interface SignInEvent {
    data class SignIn(val email: String, val password: String) : SignInEvent
}

