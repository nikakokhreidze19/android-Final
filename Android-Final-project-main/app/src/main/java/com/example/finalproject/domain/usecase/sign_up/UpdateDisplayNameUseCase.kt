package com.example.finalproject.domain.usecase.sign_up

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDisplayNameUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(fullName: String): Flow<Resource<Unit>> {
        return authRepository.updateDisplayName(fullName)
    }
}