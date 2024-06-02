package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun OkIcon(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier,
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier,
    )
}