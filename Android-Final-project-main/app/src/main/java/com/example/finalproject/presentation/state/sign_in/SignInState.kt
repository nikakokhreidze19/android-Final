package com.example.finalproject.presentation.state.sign_in


data class SignInState(
    val data: Unit? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
