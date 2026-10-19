package com.styledrop.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val StyleDropColorScheme = darkColorScheme(
    primary = DropGreen,
    secondary = DropGreenDark,
    background = DropBlack,
    surface = DropCard,
    onPrimary = DropBlack,
    onSecondary = DropWhite,
    onBackground = DropWhite,
    onSurface = DropWhite
)

@Composable
fun StyleDropTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StyleDropColorScheme,
        typography = Typography(),
        content = content
    )
}
