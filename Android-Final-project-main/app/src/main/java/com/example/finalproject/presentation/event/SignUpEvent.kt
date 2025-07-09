package com.example.finalproject.presentation.event

sealed interface SignUpEvent {
    data class SignUp(val email: String, val password: String, val fullName: String) :
        SignUpEvent
}
