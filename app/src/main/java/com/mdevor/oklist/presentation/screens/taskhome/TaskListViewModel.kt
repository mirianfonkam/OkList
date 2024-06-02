package com.mdevor.oklist.presentation.screens.taskhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdevor.oklist.data.repository.OkAuthRepository
import com.mdevor.oklist.data.repository.OkListRepository
import com.mdevor.oklist.presentation.mappers.toPresentationModel
import com.mdevor.oklist.presentation.model.TaskItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: OkListRepository,
    private val auth: OkAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    private val _vmEvent = Channel<TaskListVMEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()

    private val userEmail = auth.getUser().email

    fun fetchTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            if (userEmail.isNotBlank()) {
                repository.getAllTasks(userEmail).collect { taskListResult ->
                    _uiState.update {
                        it.copy(originalTaskList = taskListResult.toPresentationModel())
                    }
                    if (_uiState.value.isInSearchMode) {
                        updateDisplayedTaskListByFilter()
                    } else {
                        _uiState.update {
                            it.copy(filteredTaskList = it.originalTaskList)
                        }
                    }
                }
            }
        }
    }

    fun handleViewAction(viewAction: TaskListUiAction) {
        when (viewAction) {
            is TaskListUiAction.AddTask -> handleAddTask()
            is TaskListUiAction.ClickTask -> handleTaskClick(taskUuid = viewAction.taskUuid)
            is TaskListUiAction.LongPressTask -> handleTaskLongPress(taskUuid = viewAction.taskUuid)
            is TaskListUiAction.ClickSearch -> handleSearchClick()
            is TaskListUiAction.Search -> handleSearchTermChange(searchTerm = viewAction.searchTerm)
            is TaskListUiAction.ExitSearchMode -> handleSearchModeExit()
            is TaskListUiAction.ClearSearch -> handleClearSearch()
            is TaskListUiAction.ClickNotification -> handleNotificationClick()
            is TaskListUiAction.ClickProfile -> handleProfileClick()
            is TaskListUiAction.HandleViewCreation -> Unit
            is TaskListUiAction.ClickTaskGen -> handleTaskGenClick()
        }
    }

    private fun handleProfileClick() {
        viewModelScope.launch {
            _vmEvent.send(TaskListVMEvent.NavigateToProfile)
        }
    }

    private fun handleNotificationClick() {
        viewModelScope.launch {
            _vmEvent.send(TaskListVMEvent.NavigateToNotification)
        }
    }

    private fun handleClearSearch() {
        _uiState.update {
            it.copy(
                searchTerm = "",
                filteredTaskList = it.originalTaskList
            )
        }
    }

    private fun handleSearchModeExit() {
        _uiState.update {
            it.copy(
                isInSearchMode = false,
                searchTerm = "",
                filteredTaskList = it.originalTaskList
            )
        }
    }

    private fun handleSearchTermChange(searchTerm: String) {
        _uiState.update { it.copy(searchTerm = searchTerm) }
        updateDisplayedTaskListByFilter()
    }

    private fun updateDisplayedTaskListByFilter() {
        with(_uiState.value) {
            if (searchTerm.isNotBlank()) {
                val filteredTaskList = originalTaskList.filter { taskItem ->
                    shouldSelectItemBySearchQuery(taskItem, searchTerm)
                }
                _uiState.update {
                    it.copy(filteredTaskList = filteredTaskList)
                }
            } else {
                _uiState.update {
                    it.copy(filteredTaskList = originalTaskList)
                }
            }
        }
    }

    private fun shouldSelectItemBySearchQuery(
        taskItem: TaskItemData,
        searchQuery: String
    ): Boolean {
        return taskItem.title.contains(searchQuery, ignoreCase = true) ||
                taskItem.subtaskList.any { it.description.contains(searchQuery, ignoreCase = true) }
    }


    private fun handleSearchClick() {
        _uiState.update {
            it.copy(isInSearchMode = true)
        }
    }

    private fun handleAddTask() {
        viewModelScope.launch {
            _vmEvent.send(TaskListVMEvent.NavigateToCreateNewTask)
        }
    }

    private fun handleTaskClick(taskUuid: String) {
        viewModelScope.launch {
            _vmEvent.send(TaskListVMEvent.NavigateToTaskDetail(taskUuid = taskUuid))
        }
    }

    private fun handleTaskLongPress(taskUuid: String) {

    }

    private fun handleTaskGenClick() {
        // TODO: Implement task generation
    }
}