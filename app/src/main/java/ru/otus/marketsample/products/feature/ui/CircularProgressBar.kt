package ru.otus.marketsample.products.feature.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .height(56.dp)
            .width(56.dp)
            .background(Color.Transparent),
        color = colorResource(id = ru.otus.common.ui.R.color.purple_200),
        strokeWidth = 2.dp
    )
}