package com.example.finalproject.data.repository

import android.net.Uri
import com.example.finalproject.data.common.Resource
import com.example.finalproject.data.common.response_handler.HandleAuthResponse
import com.example.finalproject.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val handleAuthResponse: HandleAuthResponse
): AuthRepository {
    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String) =
        handleAuthResponse.safeAuthCall {
            auth.createUserWithEmailAndPassword(email, password).await()
        }

    override suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String) =
        handleAuthResponse.safeAuthCall {
            auth.signInWithEmailAndPassword(email, password).await()
        }

    override suspend fun updateDisplayName(fullName: String) =
        handleAuthResponse.safeAuthCall {
            currentUser?.updateProfile(userProfileChangeRequest {
                displayName = fullName
            })?.await()
        }

    override fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun signOut() = auth.signOut()

    override suspend fun setUserImage(url: String): Flow<Resource<Unit>> =
        handleAuthResponse.safeAuthCall {
            currentUser?.updateProfile(userProfileChangeRequest {
                photoUri = Uri.parse(url)
            })?.await()
        }

}