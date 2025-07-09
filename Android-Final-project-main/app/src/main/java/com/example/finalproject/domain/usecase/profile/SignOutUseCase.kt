package com.example.finalproject.domain.usecase.profile

import com.example.finalproject.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke() {
        return authRepository.signOut()
    }
}