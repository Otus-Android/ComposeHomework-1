package ru.otus.marketsample.promo.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import theme.MarketSampleTheme
import theme.shadow
import theme.transparent
import theme.white

@Composable
fun PromoListItem(
    promoState: PromoState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        AsyncImage(
            model = promoState.image,
            contentDescription = null,
            modifier = Modifier.height(250.dp),
            contentScale = ContentScale.Crop,
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .drawBehind {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                shadow,
                                transparent,
                            ),
                            start = Offset(x = size.width, y = size.height),
                            end = Offset(x = size.width, y = 0f)
                        ),
                    )
                }
                .zIndex(1f),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .zIndex(2f),
        ) {
            Text(
                text = promoState.name,
                color = white,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = promoState.description,
                color = white,
                fontSize = 14.sp,
                lineHeight = 15.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PromoListItemPreview() {
    MarketSampleTheme {
        PromoListItem(
            promoState = PromoState(
                id = "1",
                name = "Name",
                description = "Bla bla bla\nBla bla",
                image = "image",
            )
        )
    }
}
