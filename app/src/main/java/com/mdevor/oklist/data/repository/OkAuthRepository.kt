package com.mdevor.oklist.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.mdevor.oklist.data.datasource.remote.UserRemoteEntity

interface OkAuthRepository {
    val currentUser: FirebaseUser?

    suspend fun firebaseSignUpWithEmailAndPassword(
        email: String,
        password: String
    ): Response<Boolean>

    suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): Response<Boolean>

    suspend fun sendPasswordResetEmail(email: String): Response<Boolean>

    fun signOut()

    fun getUser(): UserRemoteEntity

    fun isLogged(): Boolean

    suspend fun signInWithGoogle(context: Context): Response<Boolean>
}

sealed class Response<out T> {
    data class Success<out T>(
        val data: T
    ) : Response<T>()

    data class Failure(
        val e: Exception
    ) : Response<Nothing>()
}