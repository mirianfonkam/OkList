package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.presentation.theme.OkListGrey500

@Composable
fun OkLineDivider(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        color = OkListGrey500,
        thickness = 1.dp,
        modifier = modifier
    )
}