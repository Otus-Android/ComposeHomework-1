package ru.otus.marketsample.details.feature.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.details.feature.DetailsScreenState
import ru.otus.marketsample.details.feature.DetailsState
import ru.otus.marketsample.products.feature.compose.Discount
import theme.LocalCustomColors
import theme.MarketSampleTheme

@Composable
fun DetailsScreen(
    state: DetailsScreenState,
    errorHasShown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.hasError -> {
                Toast.makeText(context, state.errorProvider(context), Toast.LENGTH_SHORT).show()
                errorHasShown()
            }

            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                Content(state)
            }
        }
    }
}

@Composable
private fun Content(state: DetailsScreenState) {
    Column(
        horizontalAlignment = Alignment.End
    ) {

        AsyncImage(
            model = state.detailsState.image,
            contentDescription = null,
            modifier = Modifier.height(300.dp)
        )

        Row {
            Text(
                text = state.detailsState.name,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        if (state.detailsState.discount.isNotBlank()) {
            Discount(
                state.detailsState.discount,
                modifier = Modifier
            )
        }

        Text(
            text = state.detailsState.price,
            color = LocalCustomColors.current.purple500,
            fontSize = 18.sp,
            modifier = Modifier.padding(14.dp)
        )

        Button(
            onClick = { },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "ADD TO CART", fontSize = 18.sp)
        }
    }
}

@Composable
@PreviewLightDark
private fun DetailsScreenPreview() {
    MarketSampleTheme {
        DetailsScreen(
            state = DetailsScreenState(
                isLoading = false,
                detailsState = DetailsState(
                    id = "1",
                    name = "Product Name",
                    image = "url",
                    price = "2000 руб",
                    hasDiscount = true,
                    discount = "-20%"
                ),
                hasError = false,
            ),
            errorHasShown = { }
        )
    }
}