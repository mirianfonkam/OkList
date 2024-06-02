package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.model.TaskItemData
import com.mdevor.oklist.presentation.screens.taskhome.TaskListUiAction
import com.mdevor.oklist.presentation.theme.OkListFontSmall
import com.mdevor.oklist.presentation.theme.OkListTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OkTaskItem(task: TaskItemData, viewAction: (TaskListUiAction) -> Unit) {
    val taskColor = Color(color = task.color)
    Surface(
        modifier = Modifier
            .height(132.dp)
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = { viewAction(TaskListUiAction.LongPressTask(task.uuid)) },
                onClick = { viewAction(TaskListUiAction.ClickTask(task.uuid)) },
            ),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.onBackground,
        shape = AbsoluteRoundedCornerShape(
            topLeftPercent = 12,
            bottomLeftPercent = 12
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(0.7f)) {
                OkTaskColorIndicator(
                    modifier = Modifier
                        .height(60.dp)
                        .width(6.dp)
                        .align(androidx.compose.ui.Alignment.CenterVertically),
                    color = taskColor
                )
                Column(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.spacing_medium),
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_x_small)),
                        text = task.title,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = task.timeToCompletionInfo,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            OkTaskProgressCircle(
                modifier = Modifier
                    .size(90.dp)
                    .padding(
                        end = dimensionResource(id = R.dimen.spacing_medium),
                    ),
                progressPercentageValue = task.progressPercentage,
                progressColor = taskColor,
                progressTextSize = OkListFontSmall,
                progressTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OkTaskItemPreview() {
    OkListTheme {
        OkTaskItem(
            task = TaskItemData(
                uuid = "1",
                title = "Buy Groceries",
                timeToCompletionInfo = "12 hours left",
                deadlineDate = "2021-12-31",
                progressPercentage = 70,
                color = 0xFF3957C5,
                subtaskList = emptyList(),
                tagList = emptyList()
            ),
            viewAction = {}
        )
    }
}