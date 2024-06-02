package com.mdevor.oklist

import com.mdevor.oklist.presentation.model.SubtaskItemData
import com.mdevor.oklist.presentation.model.TaskItemData
import com.mdevor.oklist.presentation.screens.detailtask.DetailTaskUiState
import com.mdevor.oklist.presentation.screens.taskhome.TaskListUiState
import com.mdevor.oklist.presentation.theme.TaskColor

private fun createMockedInitialState() = DetailTaskUiState(
    title = "Task 1",
    taskColorIndicator = TaskColor.OK_LIST_BLUE.color,
    subtasks = listOf(
        SubtaskItemData(
            uuid = "2",
            description = "Subtask 1",
            isCompleted = false
        ),
        SubtaskItemData(
            uuid = "3",
            description = "Subtask 2",
            isCompleted = true
        ),
        SubtaskItemData(
            uuid = "4",
            description = "Subtask 3",
            isCompleted = false
        )
    )
)

private fun mockUiState(): TaskListUiState {
    return TaskListUiState(
        filteredTaskList = listOf(
            TaskItemData(
                uuid = "1",
                title = "Buy Groceries",
                timeToCompletionInfo = "12 hours left",
                deadlineDate = "2021-12-31",
                progressPercentage = 70,
                color = TaskColor.BLUE_INDIGO.color,
                subtaskList = listOf(
                    SubtaskItemData(
                        uuid = "3",
                        description = "Subtask 1",
                        isCompleted = false
                    ),
                    SubtaskItemData(
                        uuid = "4",
                        description = "Subtask 2",
                        isCompleted = true
                    )
                ),
                tagList = emptyList()
            ),
            TaskItemData(
                uuid = "2",
                title = "Write cover letter",
                timeToCompletionInfo = "Finished!",
                deadlineDate = "2021-12-31",
                progressPercentage = 100,
                color = TaskColor.PINK.color,
                subtaskList = listOf(
                    SubtaskItemData(
                        uuid = "1",
                        description = "Subtask 1",
                        isCompleted = false
                    ),
                    SubtaskItemData(
                        uuid = "2",
                        description = "Subtask 2",
                        isCompleted = true
                    )
                ),
                tagList = emptyList()
            ),
        )
    )
}