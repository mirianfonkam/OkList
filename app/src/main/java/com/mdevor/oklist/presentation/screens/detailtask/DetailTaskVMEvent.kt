package com.mdevor.oklist.presentation.screens.detailtask

sealed class DetailTaskVMEvent {

    data object NavigateBack : DetailTaskVMEvent()
}