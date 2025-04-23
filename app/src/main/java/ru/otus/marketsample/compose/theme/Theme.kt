package ru.otus.marketsample.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import ru.otus.common.composeui.PaletteColors

private val lightScheme = lightColors(
    primary = PaletteColors.Purple500,
    primaryVariant = PaletteColors.Purple700,
    onPrimary = PaletteColors.White,
    secondary = PaletteColors.Teal200,
    secondaryVariant = PaletteColors.Teal700,
    onSecondary = PaletteColors.Black,
)

private val darkScheme = darkColors(
    primary = PaletteColors.Purple200,
    primaryVariant = PaletteColors.Purple700,
    onPrimary = PaletteColors.Black,
    secondary = PaletteColors.Teal200,
    secondaryVariant = PaletteColors.Teal200,
    onSecondary = PaletteColors.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors =  if (darkTheme) darkScheme else lightScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
