package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.screens.taskhome.TaskListUiAction
import com.mdevor.oklist.presentation.theme.OkListBlueOnSurface
import com.mdevor.oklist.presentation.theme.OkListBluePrimary

@Composable
fun OkBottomBar(viewAction: (TaskListUiAction) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.spacing_medium))
            .clip(RoundedCornerShape(25.dp)),
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    ) {
        NavigationBarItem(
            icon = {
                OkIcon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.task_generation_24),
                    contentDescription = "Task Suggestions",
                    modifier = Modifier.size(32.dp),
                )
            },
            selected = false,
            onClick = {  viewAction(TaskListUiAction.ClickTaskGen) },
        )

        IconButton(
            onClick = { viewAction(TaskListUiAction.AddTask) },
            modifier = Modifier
                .size(54.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(OkListBluePrimary, OkListBlueOnSurface)
                    ),
                    shape = CircleShape,
                )
        ) {
            OkIcon(
                imageVector = Icons.Sharp.Add,
                contentDescription = "Add icon",
                modifier = Modifier.size(30.dp),
            )
        }

        NavigationBarItem(
            icon = {
                OkIcon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(36.dp),
                )
            },
            selected = false,
            onClick = { viewAction(TaskListUiAction.ClickSearch) },
        )
    }
}