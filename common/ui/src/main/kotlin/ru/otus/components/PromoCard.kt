package ru.otus.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.otus.common.ui.R

@Composable
fun PromoCard(
    modifier: Modifier = Modifier,
    image: @Composable () -> Unit,
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
) {
    val color2 = Color(0x00000000)
    val color1 = Color(0x00AA0000)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        image.invoke()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(color1, color2)
                        )
                    )
                }
                .padding(10.dp)
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            title.invoke()
            description.invoke()
        }
    }
}

@Preview
@Composable
fun PromoCard_Preview() {
    MaterialTheme {
        PromoCard(
            image = {
                Image(
                    painter = painterResource(R.drawable.cats),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            title = {
                Text("Какой-то текст заголовка")
            },
            description = {
                Text("Какой-то текст описания")
            }
        )
    }
}