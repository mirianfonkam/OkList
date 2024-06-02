package com.mdevor.oklist.presentation.screens.profile

sealed class ProfileVMEvent {

    data object NavigateBack : ProfileVMEvent()

    data object NavigateToInitialLogin : ProfileVMEvent()
}