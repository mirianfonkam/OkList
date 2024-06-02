package com.mdevor.oklist.presentation.screens.taskhome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.uicomponents.OkIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchTerm: String,
    viewAction: (TaskListUiAction) -> Unit
) {
    Column {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
            title = {
                BasicTextField(
                    value = searchTerm,
                    onValueChange = {
                        viewAction(TaskListUiAction.Search(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    cursorBrush = SolidColor(Color.White),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (searchTerm.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search_term_placeholder),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                ),
                            )
                        } else {
                            innerTextField()
                        }
                    }
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            navigationIcon = {
                OkIconButton(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                    contentDescription = "Back",
                    onClick = { viewAction(TaskListUiAction.ExitSearchMode) },
                )
            },
            actions = {
                OkIconButton(
                    imageVector = Icons.Sharp.Clear,
                    contentDescription = "Clear search",
                    onClick = { viewAction(TaskListUiAction.ClearSearch) },
                )
            },
        )
        Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacing_medium)))
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    OkListTheme {
        SearchBar(
            searchTerm = "",
            viewAction = {},
        )
    }
}