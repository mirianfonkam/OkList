package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.model.SubtaskItemData

@Composable
fun OkSubtaskItem(
    subtask: SubtaskItemData,
    onCheckedChange: (Boolean) -> Unit,
    onTextValueChange: (String) -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Checkbox(
            checked = subtask.isCompleted,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkmarkColor = MaterialTheme.colorScheme.onSecondaryContainer,
                checkedColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        )
        BasicTextField(
            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_x_small)),
            value = subtask.description,
            onValueChange = onTextValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textDecoration = if (subtask.isCompleted) TextDecoration.LineThrough else null
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimaryContainer),
        )
    }
}

@Composable
fun OkAddSubtaskItem(
    onClick: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)) {
        OkIcon(
            imageVector = Icons.Sharp.Add,
            contentDescription = "Add",
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.spacing_x_small))
                .size(20.dp),
        )
        Text(
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.spacing_small)),
            text = stringResource(R.string.add_new_item),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
        )
    }
}

@Preview
@Composable
private fun SubtaskPreview() {
//    OkListTheme {
//        Column {
//            OkSubtaskItem(
//                subtask = SubtaskItemData(
//                    uuid = UUID.randomUUID(),
//                    description = "Do something",
//                    isCompleted = false,
//
//                ),
//                onCheckedChange = { }
//            )
//            OkAddSubtaskItem()
//        }
//    }
}