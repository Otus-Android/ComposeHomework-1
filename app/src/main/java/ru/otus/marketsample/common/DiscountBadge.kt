package ru.otus.marketsample.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiscountBadge(
    modifier: Modifier = Modifier,
    discountText: String,
) {
    val purple200 = colorResource(id = ru.otus.common.ui.R.color.purple_200)
    val purple500 = colorResource(id = ru.otus.common.ui.R.color.purple_500)
    Box(
        modifier = modifier
            .padding(8.dp)
            .drawWithCache {
                val cornerRadius = 40.dp.toPx()
                val topRightRadius = 10.dp.toPx()
                val outlinePath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(Offset.Zero, size),
                            topLeft = CornerRadius(cornerRadius),
                            topRight = CornerRadius(topRightRadius),
                            bottomRight = CornerRadius(cornerRadius),
                            bottomLeft = CornerRadius(cornerRadius)
                        )
                    )
                }
                val gradientBrush = Brush.linearGradient(
                    colors = listOf(purple200, purple500),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, 0f)
                )

                onDrawWithContent {
                    drawPath(path = outlinePath, brush = gradientBrush)
                    drawPath(
                        path = outlinePath,
                        color = Color.White,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawContent()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            text = discountText,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
