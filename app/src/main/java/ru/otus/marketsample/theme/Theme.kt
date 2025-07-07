package ru.otus.marketsample.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ru.otus.marketsample.util.Dimensions
import ru.otus.marketsample.util.LocalSpacing


val appColorScheme = lightColorScheme(
    primary = Purple500,
    onPrimary = White,
    secondary = Teal200,
    onSecondary = Black,
)

@Composable
fun HomeworkCompose1Theme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colorScheme = appColorScheme,
            typography = Typography,
            content = content
        )
    }
}