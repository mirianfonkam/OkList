package com.mdevor.oklist.presentation.screens.login.createaccount

import android.content.Context

sealed class CreateAccountUiAction {

    data class UpdateEmail(val email: String) : CreateAccountUiAction()

    data class UpdatePassword(val password: String) : CreateAccountUiAction()

    data class UpdateConfirmPassword(val confirmPassword: String) : CreateAccountUiAction()

    data object ClickRegister : CreateAccountUiAction()

    data class ClickGoogle(val context: Context) : CreateAccountUiAction()

    data object ClickBack : CreateAccountUiAction()

}