package ru.otus.marketsample.products.feature.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.products.feature.ProductState

@Composable
@Preview
fun ShowProductCard() {
    ProductCard(
        ProductState(
            id = "preview_product_test_id",
            name = "Шоколадный жидкий пирог",
            hasDiscount = true,
            discount = "10",
            price = "282,0",
            image = ""
        ), Modifier
    )
}

@Composable
fun ProductCard(
    product: ProductState,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .height(160.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.weight(1f)) {
            Box {
                AsyncImage(
                    model = product.image,
                    contentDescription = "product_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(RoundedCornerShape(16.dp))
                )
                if (product.hasDiscount) Discount(
                    Modifier.align(Alignment.TopEnd),
                    product.discount
                )
            }
        }
        Column(
            Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = product.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(ru.otus.common.ui.R.color.black)
                )

            )
            Spacer(Modifier.weight(1f))

            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 0.dp,
                modifier = Modifier.align(Alignment.End),
                backgroundColor = colorResource(ru.otus.common.ui.R.color.price_background)
            ) {
                Text(
                    text = product.price,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(ru.otus.common.ui.R.color.purple_500)
                    ),
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun ShowDiscount() {
    Discount(Modifier, "10%")
}

@Composable
fun Discount(modifier: Modifier, price: String) = Box(
    modifier
        .padding(top = 4.dp, end = 8.dp)
        .background(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 4.dp,
                bottomStart = 16.dp,
                bottomEnd = 12.dp
            ),
            color = Color.White
        )
        .padding(1.dp)
        .background(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 4.dp,
                bottomStart = 16.dp,
                bottomEnd = 12.dp
            ),
            brush = Brush.linearGradient(
                colors = listOf(
                    colorResource(ru.otus.common.ui.R.color.purple_500),
                    colorResource(ru.otus.common.ui.R.color.purple_200)
                )
            )
        )
) {
    Text(
        text = price,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(ru.otus.common.ui.R.color.white)
        ),
        modifier = Modifier
            .padding(
                horizontal = 6.dp,
                vertical = 2.dp
            )
            .align(Alignment.Center)
    )
}