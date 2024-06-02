package com.mdevor.oklist.data.datasource.remote

data class UserRemoteEntity(
    val email: String,
    val displayName: String,
    val photoUrl: String,
    val uid: String
)