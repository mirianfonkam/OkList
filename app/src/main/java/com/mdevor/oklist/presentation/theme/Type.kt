package com.mdevor.oklist.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mdevor.oklist.R

private val PoppinsFont = FontFamily(
    Font(R.font.poppins),
    Font(R.font.poppins_semibold, FontWeight.W600),
    Font(R.font.poppins_bold, FontWeight.W700),
)
private val UbuntuFont = FontFamily(
    Font(R.font.ubuntu),
    Font(R.font.ubuntu_bold, FontWeight.W700),
)

val OkListFontXSmall = 14.sp
val OkListFontSmall = 16.sp
val OkListFontMedium = 18.sp
val OkListFontLarge = 24.sp

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = UbuntuFont,
        fontWeight = FontWeight.W700,
        fontSize = OkListFontLarge,
    ),
    displayMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontWeight = FontWeight.W600,
        fontSize = OkListFontMedium,
    ),
    bodyLarge = TextStyle(
        fontFamily = PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = OkListFontMedium,
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = OkListFontXSmall,
    ),
    labelMedium = TextStyle(
        fontFamily = UbuntuFont,
        fontWeight = FontWeight.W700,
        fontSize = OkListFontXSmall,
    ),
    labelSmall = TextStyle(
        fontFamily = UbuntuFont,
        fontWeight = FontWeight.Normal,
        fontSize = OkListFontXSmall,
    ),
)