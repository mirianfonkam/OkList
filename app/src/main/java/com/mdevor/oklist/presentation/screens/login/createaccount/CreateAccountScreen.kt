package com.mdevor.oklist.presentation.screens.login.createaccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.mdevor.oklist.presentation.uicomponents.OkTextButton
import com.mdevor.oklist.presentation.uicomponents.OkTextInputField
import com.mdevor.oklist.presentation.uicomponents.OkToolbar
import com.mdevor.oklist.presentation.uicomponents.archcomponent.SingleEventEffect

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    onSignInWithGoogle: () -> Unit,
) {
    val viewState: CreateAccountUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val viewAction: (CreateAccountUiAction) -> Unit = { viewModel.handleViewAction(it) }

    CreateAccountScreenContent(
        viewState = viewState,
        viewAction = viewAction,
    )
    SingleEventEffect(sideEffect = viewModel.vmEvent) { vmEvent ->
        when (vmEvent) {
            is CreateAccountVMEvent.NavigateBack -> onBackClick()
            is CreateAccountVMEvent.NavigateToLogin -> onRegisterClick()
            is CreateAccountVMEvent.NavigateToTaskHome -> onSignInWithGoogle()
        }
    }
}

@Composable
private fun CreateAccountScreenContent(
    viewState: CreateAccountUiState,
    viewAction: (CreateAccountUiAction) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OkToolbar(
            leadingIcon = {
                OkIconButton(
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacing_small)),
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = "Navigate Back",
                    onClick = { viewAction(CreateAccountUiAction.ClickBack) }
                )
            }
        )
        OkListLogo()
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_large)),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            OkTextInputField(
                text = viewState.email,
                onTextChange = { viewAction(CreateAccountUiAction.UpdateEmail(it)) },
                labelText = "E-mail",
            )
            OkTextInputField(
                text = viewState.password,
                onTextChange = { viewAction(CreateAccountUiAction.UpdatePassword(it)) },
                labelText = "Password",
            )
            OkTextInputField(
                text = viewState.confirmPassword,
                onTextChange = { viewAction(CreateAccountUiAction.UpdateConfirmPassword(it)) },
                labelText = "Confirm password"
            )
            OkTextButton(
                text = stringResource(R.string.register),
                onClick = { viewAction(CreateAccountUiAction.ClickRegister) }
            )
        }
        SocialConnectTitleFooter()
        val context = LocalContext.current
        SocialConnectOptionItemsFooter(
            onGoogleClick = { viewAction(CreateAccountUiAction.ClickGoogle(context)) }
        )
    }
}


@Preview(backgroundColor = 0xFF2B2A3C)
@Composable
fun CreateAccountScreenPreview() {
    OkListTheme {
        CreateAccountScreenContent(
            viewState = CreateAccountUiState(),
            viewAction = { }
        )
    }
}