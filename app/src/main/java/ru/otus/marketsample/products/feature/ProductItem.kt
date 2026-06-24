package ru.otus.marketsample.products.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductItem(
    product: ProductState,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onClick(product.id)
            }
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProductImage(
            product = product,
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        ProductInfo(
            product = product,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
private fun ProductImage(
    product: ProductState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.height(130.dp)
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
        )

        if (product.hasDiscount) {
            DiscountBadge(
                discountText = product.discount,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DiscountBadge(
    discountText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(
                    topEnd = 10.dp,
                    topStart = 40.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(id = ru.otus.common.ui.R.color.purple_500),
                        colorResource(id = ru.otus.common.ui.R.color.purple_200)
                    )
                ),
                shape = RoundedCornerShape(
                    topEnd = 10.dp,
                    topStart = 40.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
    ) {
        Text(
            text = discountText,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(
                    horizontal = 10.dp,
                    vertical = 4.dp
                )
        )
    }
}

@Composable
private fun ProductInfo(
    product: ProductState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(130.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = product.name,
            fontSize = 18.sp,
            maxLines = 2,
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            overflow = TextOverflow.Ellipsis
        )

        PriceChip(
            price = product.price,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun PriceChip(
    price: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(id = ru.otus.common.ui.R.color.beige),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = price,
            color = colorResource(id = ru.otus.common.ui.R.color.purple_500),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        )
    }
}