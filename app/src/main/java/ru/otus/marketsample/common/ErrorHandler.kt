package ru.otus.marketsample.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ErrorHandler(
    hasError: Boolean,
    onErrorShown: () -> Unit,
) {
    val context = LocalContext.current

    if (hasError) {
        Toast.makeText(
            context,
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
        onErrorShown.invoke()
    }
}