package com.mdevor.oklist.presentation.screens.taskhome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.model.TaskItemData
import com.mdevor.oklist.presentation.screens.taskhome.feedbackscreens.EmptyTaskStateScreen
import com.mdevor.oklist.presentation.screens.taskhome.feedbackscreens.NoSearchResultScreen
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.uicomponents.OkBottomBar
import com.mdevor.oklist.presentation.uicomponents.OkTaskItem
import com.mdevor.oklist.presentation.uicomponents.OkTopBar
import com.mdevor.oklist.presentation.uicomponents.archcomponent.SingleEventEffect

@Composable
fun TaskHomeScreen(
    viewModel: TaskListViewModel,
    onPlusClick: () -> Unit,
    onTaskClick: (String) -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    val viewState: TaskListUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val viewAction: (TaskListUiAction) -> Unit = { viewModel.handleViewAction(it) }

    TaskHomeScreenContent(
        viewState = viewState,
        viewAction = viewAction,
    )
    SingleEventEffect(viewModel.vmEvent) { vmEvent ->
        when (vmEvent) {
            is TaskListVMEvent.NavigateToCreateNewTask -> onPlusClick()
            is TaskListVMEvent.NavigateToTaskDetail -> onTaskClick(vmEvent.taskUuid)
            is TaskListVMEvent.NavigateToNotification -> onNotificationClick()
            is TaskListVMEvent.NavigateToProfile -> onProfileClick()
        }
    }
    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        viewModel.fetchTasks()
    }
    BackHandler(
        enabled = viewState.isInSearchMode,
    ) {
        viewAction(TaskListUiAction.ExitSearchMode)
    }
}

@Composable
fun TaskHomeScreenContent(
    viewState: TaskListUiState,
    viewAction: (TaskListUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            if (viewState.isInSearchMode) {
                SearchBar(
                    searchTerm = viewState.searchTerm,
                    viewAction = viewAction,
                )
            } else {
                OkTopBar(
                    onProfileClick = { viewAction(TaskListUiAction.ClickProfile) },
                )
            }
        },
        content = { paddingValues ->
            when {
                viewState.filteredTaskList.isEmpty() && viewState.isInSearchMode -> {
                    NoSearchResultScreen(paddingValues)
                }

                viewState.filteredTaskList.isEmpty() -> {
                    EmptyTaskStateScreen(paddingValues)
                }

                else -> {
                    TaskListScreen(
                        taskList = viewState.filteredTaskList,
                        viewAction = viewAction,
                        paddingValues = paddingValues
                    )
                }
            }
        },
        bottomBar = {
            if (viewState.isInSearchMode.not()) {
                OkBottomBar(
                    viewAction = viewAction
                )
            }
        }
    )
}

@Composable
fun TaskListScreen(
    taskList: List<TaskItemData>,
    viewAction: (TaskListUiAction) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = dimensionResource(id = R.dimen.spacing_medium))
    ) {
        items(taskList) { task ->
            OkTaskItem(
                task = task,
                viewAction = viewAction,
            )
            Spacer(
                modifier =
                Modifier.padding(top = dimensionResource(id = R.dimen.spacing_x_small))
            )
        }
    }
}

@Preview(backgroundColor = 0xFF2B2A3C)
@Composable
fun TaskListScreenPreview() {
    OkListTheme {
        TaskHomeScreenContent(
            viewState = TaskListUiState(
                filteredTaskList = emptyList()
            ),
            viewAction = {},
        )
    }
}