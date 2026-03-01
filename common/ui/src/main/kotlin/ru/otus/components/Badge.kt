package ru.otus.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.otus.common.ui.R

@Composable
fun Badge(
    value: String,
    modifier: Modifier = Modifier,
) {
    val color1 = colorResource(R.color.purple_500)
    val color2 = colorResource(R.color.purple_700)
    Text(
        text = value,
        modifier = modifier
            .padding(8.dp)
            .graphicsLayer {
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 4.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 20.dp,
                )
                clip = true
            }
            .border(
                2.dp,
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 4.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 20.dp,
                )
            )
            .drawBehind {
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(color1, color2)
                    )
                )
            }
            .padding(horizontal = 10.dp, vertical = 4.dp),
        color = colorResource(R.color.white),
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview
@Composable
private fun Badge_Preview() {
    MaterialTheme {
        Badge(
            value = "20%"
        )
    }
}