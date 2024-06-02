package com.mdevor.oklist.presentation.model

data class TaskItemData(
    val uuid: String,
    val title: String,
    val timeToCompletionInfo: String,
    val deadlineDate: String,
    val progressPercentage: Int,
    val color: Long,
    val subtaskList: List<SubtaskItemData>,
    val tagList: List<TagItemData>,
)