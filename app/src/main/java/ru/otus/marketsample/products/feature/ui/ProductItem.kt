package ru.otus.marketsample.products.feature.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.products.feature.ProductState

val discountModifier = Modifier
    .background(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFBB86FC), Color(0xFF6200EE)), // purple_200 -> purple_500
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f) // Угол 45 градусов
        ),
        shape = RoundedCornerShape(
            topStart = 40.dp,
            bottomStart = 40.dp,
            bottomEnd = 40.dp,
            topEnd = 10.dp
        )
    )
    .border(
        width = 2.dp,
        color = Color.White,
        shape = RoundedCornerShape(
            topStart = 40.dp,
            bottomStart = 40.dp,
            bottomEnd = 40.dp,
            topEnd = 10.dp
        )
    )

@Composable
fun ProductItem(
    product: ProductState,
    onProductClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable { onProductClick(product.id) }
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(130.dp)
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxSize(),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (product.hasDiscount) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .then(discountModifier)
                ) {
                    Text(
                        text = product.discount,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = product.name,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFF3E0), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(  R.string.price_with_arg, product.price),
                        color = Color(0xFF6200EE),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Product Item with Discount")
@Composable
fun ProductItemDiscountPreview() {
    Column(modifier = Modifier.background(Color.White)) {
        ProductItem(
            product = ProductState(
                id = "2",
                name = "Крутые беспроводные наушники с шумоподавлением",
                image = "https://example.com/image.png",
                price = "15 000",
                hasDiscount = true,
                discount = "-20%"
            ),
            onProductClick = {}
        )
    }
}