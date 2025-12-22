package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = purple_500,
    onPrimary = white,
    secondary = purple_700,
    onSecondary = black,
    tertiary = teal_200,
    onTertiary = black,
    background = light_background,
    surfaceContainer = light_background,
    onSurface = dark_background,
)

private val DarkColors = darkColorScheme(
    primary = purple_200,
    onPrimary = black,
    secondary = purple_700,
    onSecondary = black,
    tertiary = teal_200,
    onTertiary = black,
    background = black,
    surfaceContainer = dark_background,
    onSurface = light_background,
)

@Composable
fun MarketSampleTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content,
    )
}
