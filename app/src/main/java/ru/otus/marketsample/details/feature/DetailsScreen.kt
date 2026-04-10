package ru.otus.marketsample.details.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun DetailsScreen(state: DetailsScreenState) {
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val details = state.detailsState

    Column(
        modifier = Modifier
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
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp, end = 10.dp)
        )

        if (details.hasDiscount) {
            Text(
                text = details.discount,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 10.dp, top = 5.dp)
                    .background(Color(0xFFFF0000))
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )
        }

        Text(
            text = "${details.price} руб",
            color = Color(0xFF6200EE),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.End)
                .padding(14.dp)
        )

        Button(
            onClick = { /* Добавить в корзину */ },
            modifier = Modifier
                .align(Alignment.End)
                .padding(10.dp)
        ) {
            Text(text = "Add to cart", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    MaterialTheme {
        DetailsScreen(
            state = DetailsScreenState(
                isLoading = false,
                detailsState = DetailsState(
                    id = "1",
                    name = "MacBook Pro 14",
                    price = "150 000",
                    hasDiscount = true,
                    discount = "-10%",
                    image = ""
                )
            )
        )
    }
}