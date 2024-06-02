package com.mdevor.oklist.presentation.screens.taskhome

sealed class TaskListUiAction {

    data object AddTask : TaskListUiAction()

    data class ClickTask(val taskUuid: String) : TaskListUiAction()

    data object ClickProfile : TaskListUiAction()

    data object ClickSearch : TaskListUiAction()

    data object ClickNotification : TaskListUiAction()

    data object HandleViewCreation : TaskListUiAction()

    data class LongPressTask(val taskUuid: String) : TaskListUiAction()

    data class Search(val searchTerm: String) : TaskListUiAction()

    data object ClearSearch : TaskListUiAction()

    data object ExitSearchMode : TaskListUiAction()

    data object ClickTaskGen : TaskListUiAction()
}
