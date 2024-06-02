package com.mdevor.oklist.presentation.screens.taskhome

sealed class TaskListVMEvent {
    data object NavigateToCreateNewTask : TaskListVMEvent()

    data class NavigateToTaskDetail(val taskUuid: String) : TaskListVMEvent()

    data object NavigateToProfile : TaskListVMEvent()

    data object NavigateToNotification : TaskListVMEvent()
}