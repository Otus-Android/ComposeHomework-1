package ru.otus.marketsample.details.feature.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.details.feature.DetailsScreenState
import ru.otus.marketsample.products.feature.ui.discountModifier

@Composable
fun DetailsScreen(
    state: DetailsScreenState,
    onAddToCartClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            val details = state.detailsState

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = details.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = details.name,
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (details.hasDiscount) {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .then(discountModifier) // Используем созданный ранее стиль
                        ) {
                            Text(
                                text = details.discount,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Цена
                    Text(
                        text = stringResource(R.string.price_with_arg, details.price),
                        color = Color(0xFF6200EE), // purple_500
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 14.dp)
                    )

                    Button(
                        onClick = onAddToCartClick,
                        modifier = Modifier.padding(top = 10.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text(text = "Add to cart", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}