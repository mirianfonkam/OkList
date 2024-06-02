package com.mdevor.oklist.presentation.screens.detailtask

import com.mdevor.oklist.presentation.model.SubtaskItemData
import com.mdevor.oklist.presentation.theme.TaskColor

data class DetailTaskUiState(
    val title: String = "Untitled",
    val taskColorIndicator: Long = TaskColor.BLUE_INDIGO.color,
    val subtasks: List<SubtaskItemData> = emptyList(),
    val shouldOpenEnableCollabDialog: Boolean = false,
    val isCollabEnabled: Boolean = false,
    val collaboratorEmail: String = "",
    val ownerEmail: String = "",
)