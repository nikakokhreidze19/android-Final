package com.example.finalproject.domain.repository

import com.example.finalproject.data.common.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): Flow<Resource<Unit>>
    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Flow<Resource<Unit>>
    suspend fun updateDisplayName(fullName: String): Flow<Resource<Unit>>
    fun getUser(): FirebaseUser?
    fun signOut()
    suspend fun setUserImage(url: String): Flow<Resource<Unit>>
}