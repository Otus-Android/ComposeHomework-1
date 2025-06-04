package ru.otus.marketsample.details.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.R


@Composable
fun ProductDetailsComposeScreen(
    state: DetailsScreenState,
) {
    when {
        state.hasError -> {
            val context = LocalContext.current
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.errorProvider(context),
                    color = Color.Red
                )
            }
        }

        else -> {
            val details = state.detailsState

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = details.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = details.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                if (details.hasDiscount) {
                    Text(
                        text = details.discount,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .background(Color.Red, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Text(
                    text = "${details.price} руб.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.End)
                ) {
                    Text(text = stringResource(R.string.add_to_cart), fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsComposeScreenPreview() {
    val mockState = DetailsScreenState(
        isLoading = false,
        hasError = false,
        detailsState = DetailsState(
            id = "123",
            name = "Шоколад молочный",
            image = "https://via.placeholder.com/300",
            price = "199",
            hasDiscount = true,
            discount = "-20%"
        ),
        errorProvider = { "Произошла ошибка" }
    )

    ProductDetailsComposeScreen(state = mockState,)
}
