package ru.otus.marketsample.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.marketsample.theme.ImageBackground
import ru.otus.marketsample.theme.Purple200
import ru.otus.marketsample.theme.Purple500
import ru.otus.marketsample.theme.White
import ru.otus.marketsample.util.LocalSpacing

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    productState: ProductState,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, productName, discount, price, button, progressBar) = createRefs()
        val spacing = LocalSpacing.current
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = ImageBackground)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            model = productState.image,
            contentDescription = productState.name,
            contentScale = ContentScale.Crop,
            clipToBounds = true
        )
        Text(
            modifier = Modifier
                .constrainAs(productName) {
                    start.linkTo(parent.start)
                    top.linkTo(image.bottom)
                }
                .wrapContentWidth(),
            text = productState.name,
            fontSize = 24.sp,
            color = Color.Black,
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
        if (productState.hasDiscount) {
            Text(
                modifier = Modifier
                    .constrainAs(discount) {
                        end.linkTo(parent.end)
                        top.linkTo(productName.bottom)
                    }
                    .padding(horizontal = 10.dp)
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
                text = productState.discount,
                fontSize = 20.sp,
                color = White
            )
        }
        Text(
            modifier = Modifier
                .constrainAs(price) {
                    end.linkTo(parent.end)
                    if (productState.hasDiscount) {
                        top.linkTo(discount.bottom)
                    } else {
                        top.linkTo(productName.bottom)
                    }
                }
                .padding(14.dp),
            text = productState.price,
            fontSize = 16.sp,
            color = Purple500,
            fontWeight = Bold
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(price.bottom)
                },
        ) {
            Button(
                contentPadding = PaddingValues(),
                shape = MaterialTheme.shapes.small,
                onClick = { onClick() }
            ) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "Add to cart",
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailScreenStatePreview() {
    DetailScreen(
        Modifier.fillMaxSize(),
        onClick = {},
        isLoading = true,
        productState = ProductState(
            id = "123",
            name = "Product Name",
            image = "https://archive.smashing.media/assets/344dbf88-fdf9-42bb-adb4-46f01eedd629/68dd54ca-60cf-4ef7-898b-26d7cbe48ec7/10-dithering-opt.jpg",
            price = "2000 asd",
            discount = "-30%",
            hasDiscount = true,
        )
    )
}