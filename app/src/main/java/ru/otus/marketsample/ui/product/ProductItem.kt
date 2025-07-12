package ru.otus.marketsample.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.marketsample.theme.PriceBackground
import ru.otus.marketsample.theme.Purple200
import ru.otus.marketsample.theme.Purple500
import ru.otus.marketsample.theme.White
import ru.otus.marketsample.util.LocalSpacing

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    productState: ProductState,
    onClick: (id: String) -> Unit,
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .background(color = White)
            .padding(horizontal = spacing.medium)
            .padding(vertical = spacing.small + spacing.medium)
            .clickable(onClick = { onClick(productState.id) }),
    ) {
        Box(
            modifier = Modifier
                .height(130.dp)
                .weight(1f),
        ) {
            AsyncImage(
                model = productState.image,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(spacing.small + spacing.extraSmall)),
                contentDescription = productState.name,
                contentScale = ContentScale.Crop
            )
            if (productState.hasDiscount) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(spacing.small)
                        .border(
                            2.dp, White, shape = RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 10.dp,
                                bottomEnd = 40.dp,
                                bottomStart = 40.dp
                            )
                        )
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Purple500, Purple200),
                                start = Offset(0f, 0f),
                                end = Offset(Double.POSITIVE_INFINITY.toFloat(), Double.POSITIVE_INFINITY.toFloat())
                            ),
                            shape = RoundedCornerShape(
                                topStart = 40.dp,
                                topEnd = 10.dp,
                                bottomEnd = 40.dp,
                                bottomStart = 40.dp
                            )

                        )
                        .padding(horizontal = 10.dp, vertical = spacing.extraSmall),
                    text = productState.discount,
                    fontSize = 14.sp,
                    fontWeight = Bold,
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
                text = productState.name,
                maxLines = 2,
                fontSize = 18.sp,
                fontFamily = SansSerif,
                fontWeight = FontWeight.Medium,
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
                text = stringResource(R.string.price_with_arg, productState.price),
                fontSize = 16.sp,
                color = Purple500,
                fontWeight = Bold
            )
        }
    }
}
