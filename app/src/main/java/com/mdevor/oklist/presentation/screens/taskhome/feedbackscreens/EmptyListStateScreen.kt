package com.mdevor.oklist.presentation.screens.taskhome.feedbackscreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.mdevor.oklist.R

@Composable
fun EmptyTaskStateScreen(paddingValues: PaddingValues) {
    TaskResultFeedbackScreen(
        paddingValues = paddingValues,
        imageDrawable = R.drawable.ok_illu_list_empty,
        imageContentDescription = R.string.empty_task_list_image_desc,
        taskResultTitle = R.string.empty_task_list_title,
        taskResultDescription = R.string.empty_task_list_subtitle,
    )
}