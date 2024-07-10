package com.mdevor.oklist.presentation.screens.login.createaccount

data class CreateAccountUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
)