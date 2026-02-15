package ru.otus.marketsample.products.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProductCard(
    product: ProductState,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable { onItemClicked(product.id) }
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(130.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = Color.LightGray.copy(alpha = 0.3f)),
                contentScale = ContentScale.Crop
            )

            if (product.hasDiscount) {
                Text(
                    text = product.discount,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFBB86FC), Color(0xFF6200EE)),
                            ),
                            shape = RoundedCornerShape(
                                topEnd = 10.dp,
                                bottomStart = 40.dp,
                                topStart = 40.dp,
                                bottomEnd = 40.dp
                            )
                        )
                        .border(
                            2.dp,
                            Color.White,
                            RoundedCornerShape(
                                topEnd = 10.dp,
                                bottomStart = 40.dp,
                                topStart = 40.dp,
                                bottomEnd = 40.dp
                            )
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.name,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.price,
                modifier = Modifier
                    .align(Alignment.End)
                    .background(
                        color = Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color(0xFF6200EE),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        product = ProductState(
            id = "1",
            name = "Product Name",
            image = "",
            price = "2000 руб",
            hasDiscount = true,
            discount = "-20%"
        ),
        onItemClicked = {}
    )
}
