package com.mdevor.oklist.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.mdevor.oklist.data.datasource.remote.UserRemoteEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : OkAuthRepository {
    override val currentUser get() = auth.currentUser

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ) = try {
        auth.createUserWithEmailAndPassword(email, password).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String
    ) = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun getUser(): UserRemoteEntity = UserRemoteEntity(
        email = currentUser?.email.orEmpty(),
        displayName = currentUser?.displayName.orEmpty(),
        photoUrl = currentUser?.photoUrl.toString(),
        uid = currentUser?.uid.toString()
    )

    override fun isLogged(): Boolean = currentUser != null

    override suspend fun sendPasswordResetEmail(email: String) = try {
        auth.sendPasswordResetEmail(email).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override fun signOut() = auth.signOut()

    override suspend fun signInWithGoogle(context: Context): Response<Boolean> {
        // TODO: Implement Google Credentials API Reference: https://www.youtube.com/watch?v=P_jZMDmodG4
        return try {
            TODO()
        } catch (e: NotImplementedError) {
            Response.Failure(Exception(e.message))
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(): OkAuthRepository =
        OkAuthRepositoryImpl(
            auth = FirebaseAuth.getInstance(),
        )
}