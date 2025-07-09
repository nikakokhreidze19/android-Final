package com.example.finalproject.domain.usecase.sign_in

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> =
        authRepository.firebaseSignInWithEmailAndPassword(email, password)
}