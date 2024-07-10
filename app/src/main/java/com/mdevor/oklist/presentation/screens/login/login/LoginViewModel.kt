package com.mdevor.oklist.presentation.screens.login.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdevor.oklist.data.repository.OkAuthRepository
import com.mdevor.oklist.data.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: OkAuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _vmEvent = Channel<LoginVMEvent>()
    val vmEvent = _vmEvent.receiveAsFlow()

    fun handleViewAction(viewAction: LoginUiAction) {
        when (viewAction) {
            is LoginUiAction.UpdateEmail -> handleEmailUpdate(viewAction.email)
            is LoginUiAction.UpdatePassword -> handlePasswordUpdate(viewAction.password)
            is LoginUiAction.ClickGoogle -> handleGoogleClick(viewAction.context)
            is LoginUiAction.ClickRegister -> handleRegisterClick()
            is LoginUiAction.ClickBack -> handleClickBack()
            is LoginUiAction.TogglePasswordVisibility -> handleTogglePasswordVisibility()
        }
    }

    private fun handleTogglePasswordVisibility() {
        _uiState.update {
            it.copy(isPasswordVisible = it.isPasswordVisible.not())
        }
    }

    private fun handleClickBack() {
        viewModelScope.launch {
            _vmEvent.send(LoginVMEvent.NavigateBack)
        }
    }

    private fun handlePasswordUpdate(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun handleEmailUpdate(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun handleRegisterClick() {
        with(_uiState.value) {
            if (areAllFieldsValid()) {
                viewModelScope.launch {
                    auth.firebaseSignInWithEmailAndPassword(email, password).let { response ->
                        when (response) {
                            is Response.Success -> {
                                _vmEvent.send(LoginVMEvent.NavigateToTaskHome)
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
            } else {
                Log.d("CreateAccountViewModel", "Error with fields")
            }
        }
    }

    private fun handleGoogleClick(context: Context) {
        viewModelScope.launch {
            auth.signInWithGoogle(context).let { response ->
                when (response) {
                    is Response.Success -> {
                        _vmEvent.send(LoginVMEvent.NavigateToTaskHome)
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

    private fun areAllFieldsValid(): Boolean {
        return _uiState.value.email.isNotBlank() &&
                _uiState.value.password.isNotBlank()
    }
}