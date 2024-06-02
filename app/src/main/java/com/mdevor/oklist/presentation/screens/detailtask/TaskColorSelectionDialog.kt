package com.mdevor.oklist.presentation.screens.detailtask

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.theme.TaskColor
import com.mdevor.oklist.presentation.uicomponents.OkDialog
import com.mdevor.oklist.presentation.uicomponents.OkIconButton

@Composable
fun TaskColorSelectionDialog(
    onDismissClick: () -> Unit = {},
    onColorSelected: (Long) -> Unit = {},
    colorOptions: List<TaskColor> = TaskColor.entries,
    selectedColor: Long = TaskColor.BLUE_INDIGO.color,
) {
    OkDialog(
        onDismissClick = onDismissClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_medium)),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                text = stringResource(R.string.choose_a_color),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                colorOptions.forEach { color ->
                    ColorSelector(
                        color = color.color,
                        onColorSelected = onColorSelected,
                        isSelected = color.color == selectedColor,
                    )
                }
            }
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacing_x_small)))
        }
    }
}

@Composable
private fun ColorSelector(
    color: Long,
    onColorSelected: (Long) -> Unit,
    isSelected: Boolean
) {
    Surface(
        border = colorSelectorBorderStroke(isSelected),
        modifier = Modifier.size(36.dp),
        color = Color(color),
        shape = CircleShape,
        onClick = { onColorSelected(color) },
    ) {
        if (isSelected) {
            OkIconButton(
                imageVector = Icons.Rounded.Done,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun colorSelectorBorderStroke(isSelected: Boolean) = if (isSelected) {
    BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer)
} else {
    null
}

@Preview
@Composable
private fun TaskColorPreview() {
    OkListTheme {
        TaskColorSelectionDialog()
    }
}