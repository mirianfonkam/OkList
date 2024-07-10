package com.mdevor.oklist.presentation.screens.login.createaccount

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdevor.oklist.data.repository.OkAuthRepository
import com.mdevor.oklist.data.repository.Response
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
class CreateAccountViewModel @Inject constructor(
    private val auth: OkAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState: StateFlow<CreateAccountUiState> = _uiState.asStateFlow()

    private val _vmEvent = Channel<CreateAccountVMEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()

    fun handleViewAction(viewAction: CreateAccountUiAction) {
        when (viewAction) {
            is CreateAccountUiAction.UpdateEmail -> handleEmailUpdate(viewAction.email)
            is CreateAccountUiAction.UpdatePassword -> handlePasswordUpdate(viewAction.password)
            is CreateAccountUiAction.UpdateConfirmPassword -> handleConfirmPasswordUpdate(viewAction.confirmPassword)
            is CreateAccountUiAction.ClickRegister -> handleRegisterClick()
            is CreateAccountUiAction.ClickBack -> handleBackClick()
            is CreateAccountUiAction.ClickGoogle -> handleGoogleClick(viewAction.context)
            is CreateAccountUiAction.TogglePasswordVisibility -> handleTogglePasswordVisibility()
            is CreateAccountUiAction.ToggleConfirmPasswordVisibility -> handleToggleConfirmPasswordVisibility()
        }
    }

    private fun handleToggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(isConfirmPasswordVisible = it.isConfirmPasswordVisible.not()) }
    }

    private fun handleTogglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = it.isPasswordVisible.not()) }
    }

    private fun handleConfirmPasswordUpdate(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    private fun handlePasswordUpdate(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun handleEmailUpdate(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun handleBackClick() {
        viewModelScope.launch {
            _vmEvent.send(CreateAccountVMEvent.NavigateBack)
        }
    }

    private fun handleRegisterClick() {
        with(_uiState.value) {
            if (areAllFieldsValid() && (password == confirmPassword)) {
                viewModelScope.launch(Dispatchers.IO) {
                    auth.firebaseSignUpWithEmailAndPassword(
                        email = email,
                        password = password
                    ).let { response ->
                        if (response is Response.Success) {
                            _vmEvent.send(CreateAccountVMEvent.NavigateToLogin)
                        } else {
                            Log.d(
                                "CreateAccountViewModel",
                                "Error: ${response as Response.Failure}"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun areAllFieldsValid(): Boolean {
        return _uiState.value.email.isNotBlank() &&
                _uiState.value.password.isNotBlank() &&
                _uiState.value.confirmPassword.isNotBlank()
    }

    private fun handleGoogleClick(context: Context) {
        viewModelScope.launch {
            auth.signInWithGoogle(context).let { response ->
                when (response) {
                    is Response.Success -> {
                        _vmEvent.send(CreateAccountVMEvent.NavigateToTaskHome)
                    }

                    else -> {
                        Log.d(
                            "CreateAccountViewModel",
                            "Error: ${response as Response.Failure}"
                        )
                    }
                }
            }
        }
    }
}