package com.mdevor.oklist.presentation.screens.login.login

import android.content.Context

sealed class LoginUiAction {

    data class UpdateEmail(val email: String) : LoginUiAction()

    data class UpdatePassword(val password: String) : LoginUiAction()

    data object ClickRegister : LoginUiAction()

    data class ClickGoogle(
        val context: Context
    ) : LoginUiAction()

    data object ClickBack : LoginUiAction()

    data object TogglePasswordVisibility : LoginUiAction()
}