package com.mdevor.oklist.presentation.navigation

import androidx.lifecycle.ViewModel
import com.mdevor.oklist.data.repository.OkAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StartNavigationViewModel @Inject constructor(
    authRepository: OkAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartNavigationUiState())
    val uiState: StateFlow<StartNavigationUiState> = _uiState.asStateFlow()

    init {
        updateStartDestinationByLoginStatus(isUserLogged = authRepository.isLogged())
    }

    private fun updateStartDestinationByLoginStatus(isUserLogged: Boolean) {
        val startDestination = if (isUserLogged) {
            TaskList
        } else {
            InitialLogin
        }
        _uiState.update {
            it.copy(
                startDestination = startDestination
            )
        }
    }
}