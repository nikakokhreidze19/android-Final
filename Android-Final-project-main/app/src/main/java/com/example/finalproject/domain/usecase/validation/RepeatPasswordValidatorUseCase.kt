package com.example.finalproject.domain.usecase.validation

class RepeatPasswordValidatorUseCase {
    operator fun invoke(password: String): Boolean =
        password.isNotBlank()
}