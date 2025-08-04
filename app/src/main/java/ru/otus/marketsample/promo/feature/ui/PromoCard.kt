package ru.otus.marketsample.promo.feature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoState

@Composable
@Preview
fun ShowPromoCard() {
    PromoCard(
        PromoState(
            id = "preview_product_test_id",
            name = "Шоколадное настроение",
            description = "7% скидка на все, что создано для сладкой жизни!" +
                    "Шоколадные десерты, хрустящее печенье, воздушные торты -" +
                    " идеально для семейных чаепитий или подарка себе.",
            image = ""
        ), Modifier
    )
}

@Composable
fun PromoCard(
    promo: PromoState,
    modifier: Modifier
) = Box(
    modifier
        .fillMaxWidth()
        .padding(
            vertical = 8.dp,
            horizontal = 8.dp
        )
) {
    AsyncImage(
        model = promo.image,
        contentDescription = "promo_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .align(BottomCenter)
    ) {
        Text(
            text = promo.name,
            style = TextStyle(
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(ru.otus.common.ui.R.color.white)
            ),
        )
        Text(
            text = promo.description,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(ru.otus.common.ui.R.color.white)
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}