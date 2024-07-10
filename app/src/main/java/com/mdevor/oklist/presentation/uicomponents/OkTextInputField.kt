package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.mdevor.oklist.R

@Composable
fun OkTextInputField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    labelText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable() (() -> Unit)? = null,
) {
    Column(modifier) {
        labelText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                cursorColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            shape = RectangleShape,
            textStyle = MaterialTheme.typography.bodyMedium,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon
        )
    }
}

@Composable
fun OkPasswordInputField(
    modifier: Modifier = Modifier,
    labelText: String = stringResource(id = R.string.password),
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityChange: () -> Unit,
) {
    OkTextInputField(
        modifier = modifier,
        text = password,
        onTextChange = onPasswordChange,
        labelText = labelText,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            OkIconButton(
                tint = MaterialTheme.colorScheme.secondary,
                imageVector = if (isPasswordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                },
                contentDescription = "",
                onClick = onVisibilityChange
            )
        }
    )
}