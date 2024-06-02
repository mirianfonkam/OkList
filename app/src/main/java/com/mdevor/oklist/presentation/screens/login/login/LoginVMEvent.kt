package com.mdevor.oklist.presentation.screens.login.login

sealed class LoginVMEvent {
    data object NavigateBack : LoginVMEvent()

    data object NavigateToTaskHome : LoginVMEvent()
}