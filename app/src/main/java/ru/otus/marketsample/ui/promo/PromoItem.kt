package ru.otus.marketsample.ui.promo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoState
import ru.otus.marketsample.theme.ImageBackground
import ru.otus.marketsample.theme.White

@Composable
fun PromoItem(
    modifier: Modifier = Modifier,
    promoState: PromoState,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = ImageBackground),
    ) {
        AsyncImage(
            modifier = Modifier
                .height(250.dp),
            model = promoState.image,
            contentDescription = promoState.name,
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.67f)),
                    )
                )
                .align(Alignment.BottomCenter),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = promoState.name,
                fontSize = 24.sp,
                color = White,
            )
            Text(
                text = promoState.description,
                fontSize = 14.sp,
                color = White,
            )
        }
    }
}

@Preview
@Composable
private fun PromoItemPreview() {
    PromoItem(
        promoState = PromoState(
            id = "123",
            name = "Promo name",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse non efficitur arcu. Etiam felis leo, vulputate ac nulla et, blandit luctus orci. In in volutpat velit. Aliquam ut justo non risus ultrices molestie. Cras commodo fermentum tellus, et scelerisque tortor varius et. Donec sed viverra nulla. Ut efficitur maximus nisl, non blandit dui vestibulum vitae. Cras mollis facilisis leo et malesuada.",
            image = "https://freemotion.pro/upload/iblock/bef/bef8edfb2c212002bb97be661a0558dd.png"
        )
    )
}