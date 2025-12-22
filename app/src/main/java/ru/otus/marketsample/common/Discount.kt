package ru.otus.marketsample.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import theme.MarketSampleTheme
import theme.purple_200
import theme.purple_500
import theme.white

@Composable
fun Discount(
    discount: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    val roundedCornerShape = RoundedCornerShape(
        topStart = 40.dp,
        topEnd = 10.dp,
        bottomEnd = 40.dp,
        bottomStart = 40.dp,
    )

    Text(
        text = discount,
        style = style,
        modifier = modifier
            .clip(roundedCornerShape)
            .drawBehind {
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            purple_200,
                            purple_500,
                        ),
                        start = Offset(x = 0f, y = size.height),
                        end = Offset(x = size.width, y = 0f),
                    ),
                )
            }
            .border(
                width = 2.dp,
                color = white,
                shape = roundedCornerShape,
            )
            .padding(
                vertical = 4.dp,
                horizontal = 10.dp,
            ),
    )
}

@Composable
@PreviewLightDark
private fun DiscountPreview() {
    MarketSampleTheme {
        Discount(
            discount = "-20%",
            style = MaterialTheme.typography.displayLarge,
        )
    }
}
