package com.mdevor.oklist.presentation.screens.login.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.screens.login.SocialConnectOptionItemsFooter
import com.mdevor.oklist.presentation.screens.login.SocialConnectTitleFooter
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.uicomponents.OkIconButton
import com.mdevor.oklist.presentation.uicomponents.OkListLogo
import com.mdevor.oklist.presentation.uicomponents.OkPasswordInputField
import com.mdevor.oklist.presentation.uicomponents.OkTextButton
import com.mdevor.oklist.presentation.uicomponents.OkTextInputField
import com.mdevor.oklist.presentation.uicomponents.OkToolbar
import com.mdevor.oklist.presentation.uicomponents.archcomponent.SingleEventEffect

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val viewState: LoginUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val viewAction: (LoginUiAction) -> Unit = { viewModel.handleViewAction(it) }

    LoginScreenContent(
        viewState = viewState,
        viewAction = viewAction,
    )
    SingleEventEffect(sideEffect = viewModel.vmEvent) { vmEvent ->
        when (vmEvent) {
            is LoginVMEvent.NavigateToTaskHome -> onLoginClick()
            is LoginVMEvent.NavigateBack -> onBackClick()
        }
    }
}

@Composable
private fun LoginScreenContent(
    viewState: LoginUiState,
    viewAction: (LoginUiAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OkToolbar(
            leadingIcon = {
                OkIconButton(
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacing_small)),
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = "Navigate Back",
                    onClick = { viewAction(LoginUiAction.ClickBack) }
                )
            }
        )
        OkListLogo()
        Column(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_large))
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_small)),
        ) {
            OkTextInputField(
                text = viewState.email,
                onTextChange = { viewAction(LoginUiAction.UpdateEmail(it)) },
                labelText = "E-mail"
            )
            OkPasswordInputField(
                password = viewState.password,
                onPasswordChange = { viewAction(LoginUiAction.UpdatePassword(it)) },
                isPasswordVisible = viewState.isPasswordVisible,
                onVisibilityChange = { viewAction(LoginUiAction.TogglePasswordVisibility) }
            )
            OkTextButton(
                text = stringResource(R.string.sign_in),
                onClick = { viewAction(LoginUiAction.ClickRegister) }
            )
        }
        SocialConnectTitleFooter()
        val context = LocalContext.current
        SocialConnectOptionItemsFooter(
            onGoogleClick = {
                viewAction(
                    LoginUiAction.ClickGoogle(
                        context = context
                    )
                )
            },
        )
    }
}

@Preview(backgroundColor = 0xFF2B2A3C)
@Composable
fun LoginScreenPreview() {
    OkListTheme {
        LoginScreenContent(
            viewState = LoginUiState(),
            viewAction = {}
        )
    }
}