package com.mdevor.oklist.presentation.screens.detailtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.theme.OkListGrey100
import com.mdevor.oklist.presentation.theme.OkListGrey400
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.theme.TaskColor
import com.mdevor.oklist.presentation.uicomponents.OkDialog
import com.mdevor.oklist.presentation.uicomponents.OkIcon
import com.mdevor.oklist.presentation.uicomponents.OkSimpleTextButton
import com.mdevor.oklist.presentation.uicomponents.OkTextInputField

@Composable
fun EnableCollaborationUserDialog(
    onDismissClick: () -> Unit,
    isCollabEnabled: Boolean,
    onCollabSwitchChange: (Boolean) -> Unit,
    switchEnabledColor: Long,
    collaboratorEmail: String,
    onCollaboratorEmailChange: (String) -> Unit,
    onConfirmationClick: () -> Unit
) {
    OkDialog(onDismissClick = onDismissClick) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_medium)),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.spacing_xx_small)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.allow_collaborator),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Switch(
                    checked = isCollabEnabled,
                    onCheckedChange = { onCollabSwitchChange(it) },
                    thumbContent = {
                        if (isCollabEnabled) {
                            OkIcon(
                                imageVector = Icons.Filled.Check,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                contentDescription = "",
                            )
                        } else {
                            OkIcon(
                                imageVector = Icons.Filled.Clear,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                contentDescription = "",
                            )
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = Color(switchEnabledColor),
                        uncheckedTrackColor = OkListGrey100,
                        uncheckedBorderColor = OkListGrey100,
                        uncheckedThumbColor = OkListGrey400,
                    ),
                    modifier = Modifier.scale(0.8f)
                )
            }
            OkTextInputField(
                text = collaboratorEmail,
                onTextChange = onCollaboratorEmailChange,
                labelText = "E-mail"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
            ) {
                OkSimpleTextButton(
                    onClick = onConfirmationClick,
                    text = stringResource(R.string.done),
                )
            }
        }
    }
}

@Preview
@Composable
private fun EnableCollabUserDialogPreview() {
    OkListTheme {
        val isCollabEnabled = remember {
            mutableStateOf(false)
        }
        EnableCollaborationUserDialog(
            onDismissClick = { },
            onConfirmationClick = { },
            isCollabEnabled = isCollabEnabled.value,
            onCollabSwitchChange = { isCollabEnabled.value = it },
            switchEnabledColor = TaskColor.BLUE_INDIGO.color,
            collaboratorEmail = "",
            onCollaboratorEmailChange = { },
        )
    }

}