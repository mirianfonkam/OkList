package com.mdevor.oklist.presentation.screens.profile

sealed class ProfileUiAction {

    data object ClickLogout : ProfileUiAction()

    data object ClickBack : ProfileUiAction()
}