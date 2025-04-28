package ru.otus.marketsample.products.feature.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.common.DiscountBadge
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.common.ui.R as UiR

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    state: ProductState,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp,
            )
            .height(130.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.TopEnd,
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp)),
                model = state.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            if (state.hasDiscount) {
                DiscountBadge(
                    discount = state.discount,
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f).padding(
                horizontal = 12.dp,
            ),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = state.name,
                style = TextStyle.Default.copy(
                    color = Color.Black,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,

                    ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.weight(1f))
            PriceBadge(price = state.price)
        }
    }
}

@Composable
fun PriceBadge(
    modifier: Modifier = Modifier,
    price: String,
) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(UiR.color.price_bg),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            )
    ) {
        Text(
            text = stringResource(R.string.price_with_arg, price),
            style = TextStyle.Default.copy(
                color = colorResource(UiR.color.purple_500),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        )
    }
}
