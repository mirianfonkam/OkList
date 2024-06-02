package com.mdevor.oklist.presentation.screens.taskhome.feedbackscreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.mdevor.oklist.R

@Composable
fun NoSearchResultScreen(paddingValues: PaddingValues) {
    TaskResultFeedbackScreen(
        paddingValues = paddingValues,
        imageDrawable = R.drawable.ok_illu_no_results_found,
        imageContentDescription = R.string.no_search_result_image_desc,
        taskResultTitle = R.string.no_search_result_title,
        taskResultDescription = R.string.no_search_result_subtitle,
    )
}