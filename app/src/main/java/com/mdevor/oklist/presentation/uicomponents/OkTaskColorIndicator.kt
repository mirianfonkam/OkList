package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OkTaskColorIndicator(
    modifier: Modifier = Modifier,
    color: Color
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = RoundedCornerShape(percent = 50),
        content = {}
    )
}