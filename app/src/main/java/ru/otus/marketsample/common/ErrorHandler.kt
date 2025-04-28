package ru.otus.marketsample.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import ru.otus.marketsample.LocalSnackbar

@Composable
fun ErrorHandler(
    hasError: Boolean,
    onErrorShown: () -> Unit,
) {
    val snackbarHostState = LocalSnackbar.current

    LaunchedEffect(hasError) {
        if (hasError) {
            snackbarHostState.showSnackbar(
                message = "Error wile loading data",
            )
            onErrorShown.invoke()
        }
    }
}