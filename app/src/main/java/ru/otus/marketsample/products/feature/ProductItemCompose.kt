package ru.otus.marketsample.products.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import coil.compose.AsyncImage
import ru.otus.common.ui.R


@Composable
internal fun ProductItemCompose(item: ProductState, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(all = 8.dp)
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = "Product image",
                    modifier = Modifier
                        .size(height = 130.dp, width = 190.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Transparent)
                )
                if (item.hasDiscount) {
                    Box(
                        modifier = Modifier
                            .padding(top = 14.dp, end = 14.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                color = colorResource(R.color.purple_500),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = item.discount,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(top = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable { onClick() }
                ) {
                    Text(
                        text = "${item.price} руб.",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(R.color.purple_500),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ProductItemComposePreview() {
    ProductItemCompose(
        item = ProductState(
            id = "228",
            name = "Вафли с жидким шоколадом",
            image = "https://otus-android.github.io/static/compose-hw1/img/product05.webp",
            price = "228,00",
            hasDiscount = true,
            discount = "15%"
        ),
        onClick = {}
    )
}