package com.mdevor.oklist.presentation.screens.detailtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.uicomponents.OkAddSubtaskItem
import com.mdevor.oklist.presentation.uicomponents.OkIconButton
import com.mdevor.oklist.presentation.uicomponents.OkSubtaskItem
import com.mdevor.oklist.presentation.uicomponents.OkTaskColorIndicator
import com.mdevor.oklist.presentation.uicomponents.OkToolbar
import com.mdevor.oklist.presentation.uicomponents.archcomponent.SingleEventEffect

@Composable
fun DetailTaskScreen(
    viewModel: DetailTaskViewModel,
    onBackClick: () -> Unit,
) {
    val viewState: DetailTaskUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val viewAction: (DetailTaskUiAction) -> Unit = { viewModel.handleViewAction(it) }

    DetailTaskScreenContent(
        viewState = viewState,
        viewAction = viewAction,
    )
    SingleEventEffect(viewModel.vmEvent) { vmEvent ->
        when (vmEvent) {
            is DetailTaskVMEvent.NavigateBack -> onBackClick()
        }
    }
}

@Composable
private fun DetailTaskScreenContent(
    viewState: DetailTaskUiState,
    viewAction: (DetailTaskUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            OkToolbar(
                leadingIcon = {
                    OkIconButton(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        contentDescription = "Back",
                        onClick = { viewAction(DetailTaskUiAction.ClickBackButton) },
                    )
                },
                actions = {
                    OkIconButton(
                        imageVector = ImageVector.vectorResource(id = R.drawable.color_picker),
                        contentDescription = "Color picker",
                        onClick = { viewAction(DetailTaskUiAction.ClickColorPicker) },
                    )
                    OkIconButton(
                        imageVector = ImageVector.vectorResource(id = R.drawable.group_add),
                        contentDescription = "Add collaborator",
                        onClick = { viewAction(DetailTaskUiAction.ClickAddCollaborator) },
                    )
                    OkIconButton(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete task",
                        onClick = { viewAction(DetailTaskUiAction.DeleteTask) },
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_large),
                    )
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth(),
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = AbsoluteRoundedCornerShape(
                        topLeftPercent = 8,
                        bottomLeftPercent = 8
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        OkTaskColorIndicator(
                            modifier = Modifier
                                .fillMaxHeight(0.85f)
                                .width(6.dp)
                                .align(Alignment.CenterVertically),
                            color = Color(viewState.taskColorIndicator),
                        )
                        Column(
                            modifier = Modifier.padding(
                                all = dimensionResource(id = R.dimen.spacing_medium),
                            ),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            BasicTextField(
                                value = viewState.title,
                                onValueChange = { viewAction(DetailTaskUiAction.UpdateTitle(it)) },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.displayMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                ),
                                cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
                            )
                            LazyColumn {
                                items(viewState.subtasks) { subtask ->
                                    OkSubtaskItem(
                                        subtask = subtask,
                                        onCheckedChange = {
                                            viewAction(
                                                DetailTaskUiAction.UpdateSubtaskCompletion(
                                                    subtaskUuid = subtask.uuid,
                                                    isChecked = it
                                                )
                                            )
                                        },
                                        onTextValueChange = {
                                            viewAction(
                                                DetailTaskUiAction.UpdateSubtaskDescription(
                                                    subtaskUuid = subtask.uuid,
                                                    description = it
                                                )
                                            )
                                        }
                                    )
                                }
                                item {
                                    Spacer(
                                        modifier =
                                        Modifier.padding(top = dimensionResource(id = R.dimen.spacing_x_small))
                                    )
                                    OkAddSubtaskItem(
                                        onClick = { viewAction(DetailTaskUiAction.AddSubtask) }
                                    )
                                }
                            }
                        }
                    }
                }
                if (viewState.shouldOpenEnableCollabDialog) {
                    EnableCollaborationUserDialog(
                        onDismissClick = { viewAction(DetailTaskUiAction.DismissCollabView) },
                        onConfirmationClick = { viewAction(DetailTaskUiAction.ConfirmCollabConfiguration) },
                        isCollabEnabled = viewState.isCollabEnabled,
                        onCollabSwitchChange = {
                            viewAction(
                                DetailTaskUiAction.UpdateCollabPermission(
                                    it
                                )
                            )
                        },
                        switchEnabledColor = viewState.taskColorIndicator,
                        collaboratorEmail = viewState.collaboratorEmail,
                        onCollaboratorEmailChange = {
                            viewAction(
                                DetailTaskUiAction.UpdateCollabEmail(
                                    it
                                )
                            )
                        },
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun DetailTaskScreenPreview() {
    OkListTheme {
        DetailTaskScreenContent(
            viewState = DetailTaskUiState(),
            viewAction = {},
        )
    }
}