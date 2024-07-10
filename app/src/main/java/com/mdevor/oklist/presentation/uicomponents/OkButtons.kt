package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun OkTextButton(
    text: String,
    bgColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp),
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = textColor,
        )
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
fun OkSimpleTextButton(
    onClick: () -> Unit,
    text: String,
) {
    TextButton(
        onClick = onClick,
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun OkIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit = {},
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier,
    ) {
        OkIcon(
            tint = tint,
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.size(28.dp)
        )
    }
}