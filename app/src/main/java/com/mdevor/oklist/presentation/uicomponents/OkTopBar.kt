package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R

@Composable
fun OkTopBar(
    onProfileClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.spacing_medium),
                vertical = dimensionResource(id = R.dimen.spacing_large),
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.hello),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        OutlinedIconButton(
            onClick = onProfileClick,
            modifier = Modifier.size(44.dp),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            OkIcon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Navigate to profile" ,
                modifier = Modifier.size(32.dp),
            )
        }
    }
}