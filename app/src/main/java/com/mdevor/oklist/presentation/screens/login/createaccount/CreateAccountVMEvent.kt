package com.mdevor.oklist.presentation.screens.login.createaccount

sealed class CreateAccountVMEvent {

    data object NavigateToLogin : CreateAccountVMEvent()

    data object NavigateBack : CreateAccountVMEvent()

    data object NavigateToTaskHome : CreateAccountVMEvent()
}