package com.mdevor.oklist.presentation.screens.detailtask

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdevor.oklist.data.datasource.remote.SubtaskRemoteEntity
import com.mdevor.oklist.data.datasource.remote.TaskRemoteEntity
import com.mdevor.oklist.data.repository.OkAuthRepository
import com.mdevor.oklist.data.repository.OkListRepository
import com.mdevor.oklist.presentation.model.SubtaskItemData
import com.mdevor.oklist.presentation.theme.TaskColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: OkListRepository,
    private val auth: OkAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailTaskUiState())
    val uiState: StateFlow<DetailTaskUiState> = _uiState.asStateFlow()

    private val _vmEvent = Channel<DetailTaskVMEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()

    private var taskUuid: String? = savedStateHandle.get<String>("id")

    private val loggedUserEmail = auth.getUser().email

    init {
        taskUuid?.let { id ->
            handleTaskLoad(taskUuid = id)
        } ?: run {
            _uiState.update {
                it.copy(
                    taskColorIndicator = TaskColor.entries.random().color,
                    subtasks = listOf(createNewSubtask())
                )
            }
        }
    }

    fun handleViewAction(viewAction: DetailTaskUiAction) {
        when (viewAction) {
            is DetailTaskUiAction.ClickBackButton -> handleBackClick()

            is DetailTaskUiAction.UpdateTitle -> handleTitleUpdate(title = viewAction.title)
            is DetailTaskUiAction.AddSubtask -> handleAddSubtask()
            is DetailTaskUiAction.DeleteSubtask -> handleDeleteSubtask(
                subtaskUuid = viewAction.subtaskUuid
            )

            is DetailTaskUiAction.UpdateSubtaskDescription -> handleSetSubtaskDescription(
                subtaskUuid = viewAction.subtaskUuid, description = viewAction.description
            )

            is DetailTaskUiAction.UpdateSubtaskCompletion -> handleToggleSubtaskCompletion(
                subtaskUuid = viewAction.subtaskUuid, isChecked = viewAction.isChecked
            )

            is DetailTaskUiAction.UpdateTaskColorIndicator -> handleTaskColorIndicatorUpdate(
                selectedColor = viewAction.color
            )

            is DetailTaskUiAction.DeleteTask -> handleTaskDeletion()
            is DetailTaskUiAction.ClickAddCollaborator -> handleAddCollaboratorClick()
            is DetailTaskUiAction.ClickColorPicker -> handleColorPickerClick()
            is DetailTaskUiAction.ConfirmCollabConfiguration -> handleCollabEditConfirmation()
            is DetailTaskUiAction.DismissCollabView -> handleDismissCollabView()
            is DetailTaskUiAction.UpdateCollabPermission -> handleUpdateCollabPermission(
                isChecked = viewAction.isChecked
            )

            is DetailTaskUiAction.UpdateCollabEmail -> handleCollabEmailUpdate(
                email = viewAction.email
            )

            is DetailTaskUiAction.DismissColorView -> handleDismissColorView()
        }
    }

    private fun handleDismissColorView() {
        _uiState.update { it.copy(shouldOpenColorSelectionDialog = false) }
    }

    private fun handleColorPickerClick() {
        _uiState.update { it.copy(shouldOpenColorSelectionDialog = true) }
    }

    private fun handleCollabEmailUpdate(email: String) {
        _uiState.update { it.copy(collaboratorEmail = email) }
    }

    private fun handleUpdateCollabPermission(isChecked: Boolean) {
        _uiState.update { it.copy(isCollabEnabled = isChecked) }
    }

    private fun handleCollabEditConfirmation() {
        _uiState.update { it.copy(shouldOpenEnableCollabDialog = false) }
    }

    private fun handleDismissCollabView() {
        _uiState.update { it.copy(shouldOpenEnableCollabDialog = false) }
    }

    private fun handleAddCollaboratorClick() {
        _uiState.update { it.copy(shouldOpenEnableCollabDialog = true) }
    }

    private fun handleTaskLoad(taskUuid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.getTaskById(taskUuid)?.let { taskRemoteEntity ->
                    _uiState.update {
                        it.copy(
                            title = taskRemoteEntity.title,
                            taskColorIndicator = taskRemoteEntity.color,
                            subtasks = taskRemoteEntity.subtaskList.map { subtask ->
                                SubtaskItemData(
                                    uuid = subtask.id,
                                    description = subtask.description,
                                    isCompleted = subtask.completed
                                )
                            },
                            collaboratorEmail = taskRemoteEntity.collaboratorEmail.orEmpty(),
                            isCollabEnabled = taskRemoteEntity.collaboratorEmail != null,
                            ownerEmail = taskRemoteEntity.ownerEmail
                        )
                    }
                }
            }.onFailure {
                Log.d("DetailTaskViewModel", "Error loading task: $it")
            }
        }
    }

    private fun handleBackClick() {
        val id = taskUuid ?: UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO) {
            with(_uiState.value) {
                repository.upsertTask(
                    TaskRemoteEntity(id = id,
                        title = title,
                        color = taskColorIndicator,
                        deadlineDate = "",
                        ownerEmail = ownerEmail.ifBlank { loggedUserEmail },
                        collaboratorEmail = if (isCollabEnabled) collaboratorEmail else null,
                        subtaskList = _uiState.value.subtasks.map { subtask ->
                            SubtaskRemoteEntity(
                                id = subtask.uuid,
                                description = subtask.description,
                                completed = subtask.isCompleted
                            )
                        })
                )
            }
            _vmEvent.send(DetailTaskVMEvent.NavigateBack)
        }
    }

    private fun handleTitleUpdate(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun handleToggleSubtaskCompletion(subtaskUuid: String, isChecked: Boolean) {
        _uiState.update { currentState ->
            val updatedSubtasks = currentState.subtasks.map { subtask ->
                if (subtask.uuid == subtaskUuid) {
                    subtask.copy(isCompleted = isChecked)
                } else {
                    subtask
                }
            }
            currentState.copy(subtasks = updatedSubtasks)
        }

    }

    private fun handleAddSubtask() {
        _uiState.update { currentState ->
            val newSubtask = createNewSubtask()
            currentState.copy(subtasks = currentState.subtasks + newSubtask)
        }
    }

    private fun createNewSubtask() = SubtaskItemData(
        uuid = UUID.randomUUID().toString(), description = "", isCompleted = false
    )

    private fun handleSetSubtaskDescription(subtaskUuid: String, description: String) {
        _uiState.update { currentState ->
            val updatedSubtasks = currentState.subtasks.map { subtask ->
                if (subtask.uuid == subtaskUuid) {
                    subtask.copy(description = description)
                } else {
                    subtask
                }
            }
            currentState.copy(subtasks = updatedSubtasks)
        }
    }

    private fun handleTaskDeletion() {
        viewModelScope.launch {
            taskUuid?.let { id ->
                repository.deleteTask(id)
            }
            _vmEvent.send(DetailTaskVMEvent.NavigateBack)
        }
    }

    private fun handleTaskColorIndicatorUpdate(selectedColor: Long) {
        _uiState.update {
            it.copy(taskColorIndicator = selectedColor)
        }
    }

    private fun handleDeleteSubtask(subtaskUuid: String) {

    }
}