package ru.otus.marketsample.details.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.common.ui.R
import ru.otus.marketsample.products.feature.DiscountBadge

@Composable
fun DetailsItem(
    details: DetailsState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = details.image,
            contentDescription = details.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Text(
            text = details.name,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        if (details.hasDiscount) {
            DiscountBadge(
                discountText = details.discount,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            )
       }

        Text(
            text = details.price,
            color = colorResource(id = R.color.purple_500),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(14.dp)
                .align(Alignment.End)
        )

        Button(
            onClick = { },
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End)
        ) {
            Text(
                text = "Add to cart",
                fontSize = 18.sp,
                modifier = Modifier.padding(14.dp)
            )
        }
    }
}