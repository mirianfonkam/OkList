package com.mdevor.oklist.presentation.screens.taskhome

import com.mdevor.oklist.presentation.model.TaskItemData

data class TaskListUiState(
    val filteredTaskList: List<TaskItemData> = emptyList(),
    val originalTaskList: List<TaskItemData> = emptyList(),
    val isInSearchMode: Boolean = false,
    val searchTerm: String = "",
)