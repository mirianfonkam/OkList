package com.mdevor.oklist.presentation.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.theme.OkListBeigeOnSurface
import com.mdevor.oklist.presentation.theme.OkListBluePrimary
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.uicomponents.OkIcon
import com.mdevor.oklist.presentation.uicomponents.OkIconButton
import com.mdevor.oklist.presentation.uicomponents.OkLineDivider
import com.mdevor.oklist.presentation.uicomponents.OkToolbar
import com.mdevor.oklist.presentation.uicomponents.archcomponent.SingleEventEffect

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val viewState: ProfileUiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val viewAction: (ProfileUiAction) -> Unit = { viewModel.handleViewAction(it) }

    ProfileScreenContent(
        viewState = viewState,
        viewAction = viewAction,
    )
    SingleEventEffect(viewModel.vmEvent) { vmEvent ->
        when (vmEvent) {
            is ProfileVMEvent.NavigateBack -> onBackClick()
            is ProfileVMEvent.NavigateToInitialLogin -> onLogoutClick()
        }
    }
}

@Composable
fun ProfileScreenContent(
    viewState: ProfileUiState,
    viewAction: (ProfileUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            OkToolbar(
                title = "Profile",
                leadingIcon = {
                    OkIconButton(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        contentDescription = "Back",
                        onClick = { viewAction(ProfileUiAction.ClickBack) },
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(dimensionResource(id = R.dimen.spacing_medium))
                    .fillMaxSize(),
            ) {
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_small)),
                    text = stringResource(R.string.dashboard),
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Total:",
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 14.sp,
                        ),
                    )
                    Text(
                        text = "${viewState.totalTasks}",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 36.sp,
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.spacing_small)
                        ),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    OkTaskStats(
                        statLabel = "Done",
                        statIconVector = Icons.Outlined.CheckCircle,
                        statValue = viewState.completedTasks,
                        color = OkListBluePrimary,
                    )
                    OkTaskStats(
                        statLabel = "Pending",
                        statIconVector = ImageVector.vectorResource(id = R.drawable.outline_pending_actions),
                        statValue = viewState.pendingTasks,
                        color = OkListBeigeOnSurface,
                    )
                    OkTaskStats(
                        statLabel = "Archived",
                        statIconVector = ImageVector.vectorResource(id = R.drawable.outline_visibility_off),
                        statValue = 0,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_medium)))
                OkLineDivider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(1f)
                        .padding(
                            vertical = dimensionResource(id = R.dimen.spacing_medium)
                        ),
                )
                Row(
                    modifier = Modifier.clickable { viewAction(ProfileUiAction.ClickLogout) },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OkIconButton(
                        imageVector = ImageVector.vectorResource(id = R.drawable.round_logout_24),
                        contentDescription = ""
                    )
                    Text(
                        text = stringResource(R.string.sign_out),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                    )
                }
            }
        },
    )
}

@Composable
fun OkTaskStats(
    statLabel: String = "Done",
    statIconVector: ImageVector = Icons.Outlined.CheckCircle,
    statValue: Int = 0,
    color: Color = OkListBluePrimary,
) {
    Column(
        modifier = Modifier.padding(all = dimensionResource(id = R.dimen.spacing_x_small)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OkIcon(
            imageVector = statIconVector,
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = color
        )
        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_xx_small)),
            text = statLabel,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 12.sp,
            ),
            color = color
        )
        Text(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_x_small)),
            text = "$statValue",
            style = MaterialTheme.typography.displayLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 22.sp,
            ),
        )
    }
}

@Preview
@Composable
private fun ProfileScreenContentPreview() {
    OkListTheme {
        ProfileScreenContent(
            viewState = ProfileUiState(),
            viewAction = {},
        )
    }
}