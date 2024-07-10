package com.mdevor.oklist.presentation.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mdevor.oklist.R

@Composable
fun OkListLogo() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.spacing_x_small))
                .fillMaxWidth(0.16f)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.ok_ic_oklist_logo),
            contentDescription = stringResource(R.string.img_desc_oklist_logo),
        )
    }
}