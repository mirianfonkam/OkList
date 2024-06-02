package com.mdevor.oklist.presentation.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = OkListGrey400,
    onPrimaryContainer = OkListWhitePrimary,
    surface = OkListDarkSurface,
    secondary = OkListBlackPrimary,
    onSecondaryContainer = OkListBlackPrimary,
    background = OkListGrey700,
    onBackground = OkListGrey600,
    onSurface = OkListGrey100,
)

private val LightColorScheme = lightColorScheme(
    primary = OkListBlueOnSurface,
    onPrimaryContainer = OkListPinkOnSurface,
    secondary = OkListBlackPrimary,
    background = Color.White,
)

@Composable
fun OkListTheme(
    isDarkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkTheme.not()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}