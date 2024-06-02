package com.mdevor.oklist.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdevor.oklist.data.repository.OkAuthRepository
import com.mdevor.oklist.data.repository.OkListRepository
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
class ProfileScreenViewModel @Inject constructor(
    private val authRepository: OkAuthRepository,
    private val repository: OkListRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _vmEvent = Channel<ProfileVMEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllTasks(userEmail = authRepository.getUser().email)
                .collect { taskListResult ->
                    _uiState.update {
                        it.copy(
                            totalTasks = taskListResult.size,
                            completedTasks = taskListResult.count { task ->
                                task.subtaskList.all { subtask -> subtask.completed }
                            },
                            pendingTasks = taskListResult.count { task ->
                                task.subtaskList.any { subtask -> !subtask.completed }
                            }
                        )
                    }
                }
        }
    }

    fun handleViewAction(viewAction: ProfileUiAction) {
        when (viewAction) {
            is ProfileUiAction.ClickBack -> handleClickBack()
            is ProfileUiAction.ClickLogout -> handleLogoutClick()
        }
    }

    private fun handleLogoutClick() {
        viewModelScope.launch {
            authRepository.signOut()
            _vmEvent.send(ProfileVMEvent.NavigateToInitialLogin)
        }
    }

    private fun handleClickBack() {
        viewModelScope.launch {
            _vmEvent.send(ProfileVMEvent.NavigateBack)
        }
    }
}