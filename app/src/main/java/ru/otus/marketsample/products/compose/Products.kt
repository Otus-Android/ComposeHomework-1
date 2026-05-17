package ru.otus.marketsample.products.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.marketsample.R
import ru.otus.marketsample.products.feature.ProductsScreenState
import kotlin.String


@OptIn(ExperimentalMaterial3Api::class)
@Suppress("NonSkippableComposable")
@Composable
fun ProductsList(
    state: ProductsScreenState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    onClick: (String) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
    ) {
        LazyColumn(modifier = modifier) {
            items(
                items = state.productListState,
                key = { it.id }) { product ->
                ProductItem(productState = product, onClick = { id ->
                    onClick(id)
                })
            }
        }
    }
}

@Composable
fun ProductItem(
    productState: ProductState,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .background(color = Color.White)
            .clickable(
                onClick = { onClick(productState.id) }
            )
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .height(130.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            val isPreview =
                androidx.compose.ui.platform.LocalInspectionMode.current

            if (isPreview) {//оставил для дебага, в рабочем коде бы убрал такое
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
            } else {
                AsyncImage(
                    model = productState.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
            }
            if (productState.hasDiscount) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(vertical = 4.dp, horizontal = 10.dp)
                        .padding(8.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(Color(0xFFCE93D8), Color(0xFF7E57C2))
                            ),
                            shape = RoundedCornerShape(40.dp)
                        )
                        .border(2.dp, Color.White, RoundedCornerShape(40.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = productState.discount,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = productState.name,
                maxLines = 2,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 12.dp)

            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.price_with_arg, productState.price),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(ru.otus.common.ui.R.color.purple_500),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFF3E0),
                                    Color(0xFFFFF3E0)
                                )
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    ProductItem(
        ProductState(
            id = "11",
            name = "Персиковый кранч",
            image = "https://otus-android.github.io/static/compose-hw1/img/product01.webp",
            price = "1213",
            hasDiscount = true,
            discount = "12 %",
        ),
        onClick = {}
    )
}