package ru.otus.marketsample.promo.feature.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoState


@Composable
fun PromoItem(
    state: PromoState,
) {
    Box(
        modifier = Modifier.padding(10.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        AsyncImage(
            modifier = Modifier.height(250.dp),
            model = state.image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.linearGradient(
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset.Zero,
                        colors = listOf(Color.Black, Color.Transparent)
                    )
                )
        )
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = state.name,
                style = TextStyle.Default.copy(
                    color = Color.White,
                    fontSize = 24.sp,
                )
            )
            Text(
                text = state.description,
                style = TextStyle.Default.copy(
                    color = Color.White,
                    fontSize = 14.sp,
                )
            )
        }
    }
}