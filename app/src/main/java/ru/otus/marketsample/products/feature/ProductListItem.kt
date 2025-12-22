package ru.otus.marketsample.products.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.common.Discount
import theme.MarketSampleTheme
import theme.black
import theme.cream
import theme.purple_500

@Composable
fun ProductListItem(
    productState: ProductState,
    onItemClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .height(130.dp)
            .clickable { onItemClick.invoke(productState.id) },
    ) {
        Box(modifier = Modifier.weight(1f)) {
            AsyncImage(
                model = productState.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )

            if (productState.discount.isNotBlank()) {
                Discount(
                    discount = productState.discount,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp),
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = productState.name,
                color = black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Row(modifier = Modifier.align(Alignment.End)) {
                Text(
                    text = productState.price,
                    color = purple_500,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .background(color = cream, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun ProductListItemPreview() {
    MarketSampleTheme {
        ProductListItem(
            productState = ProductState(
                id = "0",
                name = "Product Name",
                image = "",
                price = "2000 руб",
                hasDiscount = true,
                discount = "-20%",
            ),
            onItemClick = {},
        )
    }
}
