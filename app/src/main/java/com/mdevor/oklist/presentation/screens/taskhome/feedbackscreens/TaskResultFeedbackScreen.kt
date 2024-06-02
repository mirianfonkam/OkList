package com.mdevor.oklist.presentation.screens.taskhome.feedbackscreens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R

@Composable
fun TaskResultFeedbackScreen(
    paddingValues: PaddingValues,
    @DrawableRes imageDrawable: Int,
    @StringRes imageContentDescription: Int,
    @StringRes taskResultTitle: Int,
    @StringRes taskResultDescription: Int
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.width(140.dp),
            painter = painterResource(id = imageDrawable),
            contentDescription = stringResource(id = imageContentDescription),
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_medium)))
        Text(
            text = stringResource(id = taskResultTitle),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_small)))
        Text(
            text = stringResource(id = taskResultDescription),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.spacing_medium)
            )
        )
    }
}
