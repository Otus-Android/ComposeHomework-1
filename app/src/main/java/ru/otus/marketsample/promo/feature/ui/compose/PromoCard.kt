package ru.otus.marketsample.promo.feature.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.promo.feature.ui.PromoState


@Composable
fun PromoCard(state: PromoState) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            model = state.image,
            contentDescription = state.name,
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = state.name,
                fontSize = 24.sp,
                color = Color.White
            )
            Text(
                text = state.description,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PromoCardPreview() {
    PromoCard(
        state = PromoState(
            id = "1",
            name = "Шоколадное настроение",
            description = "7% скидка на всё, что создано для сладкой жизни!\n" +
                    "Шоколадные десерты, хрустящее печенье, воздушные торты - идеально для семейных чаепитий или подарка себе,",
            image = "https://media.istockphoto.com/id/522735736/photo/chocolate-background.jpg?s=612x612&w=0&k=20&c=fmBbjHi5zXpcbzAQWGy3xtPgcIJkc7eHXjZiYsi396A="
        )
    )
}