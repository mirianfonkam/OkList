package com.mdevor.oklist.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mdevor.oklist.R
import com.mdevor.oklist.presentation.uicomponents.OkLineDivider

@Composable
fun SocialConnectTitleFooter() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.spacing_large))
            .padding(top = dimensionResource(id = R.dimen.spacing_medium))
    ) {
        val (divLineLeft, txtSocialConnect, divLineRight) = createRefs()
        Text(
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            text = "Or connect using:",
            modifier = Modifier.constrainAs(
                ref = txtSocialConnect,
            ) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        OkLineDivider(
            modifier = Modifier.constrainAs(
                ref = divLineLeft
            ) {
                top.linkTo(txtSocialConnect.top)
                start.linkTo(parent.start)
                end.linkTo(anchor = txtSocialConnect.start, margin = 8.dp)
                bottom.linkTo(txtSocialConnect.bottom)
                width = Dimension.fillToConstraints
            }
        )
        OkLineDivider(
            modifier = Modifier.constrainAs(
                ref = divLineRight
            ) {
                top.linkTo(txtSocialConnect.top)
                start.linkTo(
                    anchor = txtSocialConnect.end,
                    margin = 8.dp,
                )
                end.linkTo(parent.end)
                bottom.linkTo(txtSocialConnect.bottom)
                width = Dimension.fillToConstraints
            }
        )
    }

}

@Composable
fun SocialConnectOptionItemsFooter(
    onGoogleClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_small)),
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(id = R.dimen.spacing_medium),
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ok_ic_google_logo),
            contentDescription = "Google Logo",
            modifier = Modifier.clickable { onGoogleClick() }
        )
    }
}


