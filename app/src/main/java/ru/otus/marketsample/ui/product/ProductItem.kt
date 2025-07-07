package ru.otus.marketsample.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import ru.otus.marketsample.theme.PriceBackground
import ru.otus.marketsample.theme.Purple200
import ru.otus.marketsample.theme.Purple500
import ru.otus.marketsample.theme.White
import ru.otus.marketsample.util.LocalSpacing

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    productName: String,
    productUrl: String,
    productPrice: String,
    productDiscount: String? = null,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .background(color = White)
            .padding(horizontal = spacing.medium)
            .padding(vertical = spacing.small + spacing.medium)
    ) {
        Box(
            modifier = Modifier
                .height(130.dp)
                .weight(1f),
        ) {
            AsyncImage(
                model = productUrl.toUri(),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(spacing.small + spacing.extraSmall)),
                contentDescription = productName,
                contentScale = ContentScale.FillBounds
            )
            if (productDiscount != null) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(spacing.small)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Purple500, Purple200),
                                start = Offset(0f, 0f),
                                end = Offset(50f, 50f)
                            ),
                            shape = RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 10.dp,
                                bottomEnd = 40.dp,
                                bottomStart = 40.dp
                            )
                        )
                        .border(
                            2.dp, White, shape = RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 10.dp,
                                bottomEnd = 40.dp,
                                bottomStart = 40.dp
                            )
                        )
                        .padding(horizontal = 10.dp, vertical = spacing.extraSmall),
                    text = productDiscount,
                    fontSize = 14.sp,
                    color = White
                )
            }
        }
        VerticalDivider(color = Color.LightGray, modifier = Modifier.padding(vertical = spacing.small))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium - spacing.small),
                text = productName,
                maxLines = 2,
                fontSize = 18.sp,
                fontFamily = SansSerif,
                color = Black
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = spacing.medium - spacing.small)
                    .background(
                        color = PriceBackground,
                        shape = RoundedCornerShape(spacing.small)
                    )
                    .padding(horizontal = spacing.medium - spacing.extraSmall, vertical = spacing.small)
                    .align(Alignment.BottomEnd),
                text = productPrice,
                fontSize = 16.sp,
                color = Purple500,
                fontWeight = Bold
            )
        }
    }
}

@Preview
@Composable
private fun ProductItemPreview() {
    ProductItem(
        productPrice = "10 y.e.",
        productDiscount = "-10%",
        productUrl = "https://raw.githubusercontent.com/Otus-Android/ComposeHomework-1/refs/heads/master/img/01.png",
        productName = "Product name",
    )
}