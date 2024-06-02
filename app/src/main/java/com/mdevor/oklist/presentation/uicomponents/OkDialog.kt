package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mdevor.oklist.presentation.theme.OkListGrey400

@Composable
fun OkDialog(
    onDismissClick: () -> Unit,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    Dialog(onDismissRequest = { onDismissClick() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = OkListGrey400),
            content = content
        )
    }
}