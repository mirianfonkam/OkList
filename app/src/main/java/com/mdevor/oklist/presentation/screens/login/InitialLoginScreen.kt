package com.mdevor.oklist.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.theme.OkListTheme
import com.mdevor.oklist.presentation.uicomponents.OkTextButton

@Composable
fun InitialLoginScreen(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_x_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.spacing_x_large))
                .width(150.dp),
            painter = painterResource(id = R.drawable.ok_ic_oklist_full_logo),
            contentDescription = stringResource(R.string.img_desc_oklist_logo),
        )
        Image(
            modifier = Modifier.width(150.dp),
            painter = painterResource(id = R.drawable.ok_illu_user_login),
            contentDescription = stringResource(R.string.img_desc_user_login),
        )
        Column() {
            Text(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.spacing_small))
                    .align(Alignment.Start),
                style = MaterialTheme.typography.displayLarge,
                text = stringResource(R.string.welcome),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            OkTextButton(
                text = stringResource(R.string.sign_in),
                bgColor = MaterialTheme.colorScheme.onPrimaryContainer,
                textColor = MaterialTheme.colorScheme.surface,
                onClick = onLoginClick
            )
            Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_small)))
            OkTextButton(
                text = stringResource(R.string.sign_up),
                onClick = onCreateAccountClick
            )
            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.spacing_medium)))
        }
    }
}

@Preview(backgroundColor = 0xFF2B2A3C)
@Composable
fun HomeScreenPreview() {
    OkListTheme {
        InitialLoginScreen(
            onLoginClick = {},
            onCreateAccountClick = {},
        )
    }
}