package ru.otus.marketsample.products.feature.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.otus.marketsample.products.feature.ui.ProductState

@Composable
fun ProductCard(
    state: ProductState,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val priceBackground = Color(0xFFFFF3E0)
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .height(130.dp)
            .fillMaxWidth()
            .clickable {
                navigateToDetails(state.id)
            }
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp)) // get from res
        ) {
            AsyncImage(
                model = state.image,
                contentDescription = state.name,
                modifier = Modifier
                    .fillMaxSize()
            )
            if (state.hasDiscount) {
                DiscountWidget(
                    discount = state.discount,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                text = state.name,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Text(
                text = state.price,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(ru.otus.common.ui.R.color.purple_500),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(
                        color = priceBackground,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 12.dp, horizontal = 8.dp)
            )
        }
    }

}

@Preview
@Composable
private fun ProductCardPreview() {

    val state = ProductState(
        id = "1",
        name = "Вафли с жидким шоколадом",
        image = "https://images.unsplash.com/photo-1721078917681-40f74d16d7b7?q=80&w=1023&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        price = "250,00 руб",
        hasDiscount = true,
        discount = "7%"
    )

    ProductCard(state = state, navigateToDetails = {})

}