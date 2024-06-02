package com.mdevor.oklist.presentation.mappers

import com.mdevor.oklist.data.datasource.remote.SubtaskRemoteEntity
import com.mdevor.oklist.data.datasource.remote.TaskRemoteEntity
import com.mdevor.oklist.presentation.model.SubtaskItemData
import com.mdevor.oklist.presentation.model.TaskItemData
import kotlin.math.roundToInt

fun List<TaskRemoteEntity>.toPresentationModel(): List<TaskItemData> {
    return map { taskItem ->
        TaskItemData(
            uuid = taskItem.id,
            title = taskItem.title,
            timeToCompletionInfo = "",
            deadlineDate = taskItem.deadlineDate,
            progressPercentage = taskItem.subtaskList.calculateTaskProgressPercentage(),
            color = taskItem.color,
            subtaskList = taskItem.subtaskList.map { subtaskItem ->
                SubtaskItemData(
                    uuid = subtaskItem.id,
                    description = subtaskItem.description,
                    isCompleted = subtaskItem.completed
                )
            },
            tagList = emptyList()
        )
    }
}

private fun List<SubtaskRemoteEntity>.calculateTaskProgressPercentage(): Int {
    return if (isEmpty()) {
        0
    } else {
        ((count { it.completed }.toDouble() / size.toDouble()) * 100).roundToInt()
    }
}