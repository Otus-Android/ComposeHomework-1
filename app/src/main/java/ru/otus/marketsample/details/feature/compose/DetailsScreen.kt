package ru.otus.marketsample.details.feature.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ru.otus.marketsample.details.feature.DetailsScreenState
import ru.otus.marketsample.details.feature.DetailsState
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.products.feature.ui.ProductState
import ru.otus.marketsample.products.feature.ui.compose.components.DiscountWidget

@Composable
fun DetailsScreenRoute(
    viewModel: DetailsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailsScreen(
        state = state,
        onError = viewModel::errorHasShown
    )
}

@Composable
private fun DetailsScreen(
    state: DetailsScreenState,
    onError: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    if (state.hasError) {
        Toast.makeText(
            context,
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
        onError()
    }
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = state.detailsState.image,
            contentDescription = state.detailsState.name,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = state.detailsState.name,
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            if (state.detailsState.hasDiscount) {
                DiscountWidget(discount = state.detailsState.discount)
            }
            Text(
                text = state.detailsState.price,
                fontSize = 18.sp,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(vertical = 14.dp)
            )

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "Add to cart", fontSize = 18.sp)
            }
        }
    }

}


@Preview
@Composable
private fun DetailsScreenPreview() {
    val product = ProductState(
        id = "1",
        name = "Вафли с жидким шоколадом",
        image = "",
        price = "250.00 руб",
        hasDiscount = true,
        discount = "7%"
    )
    val state = DetailsScreenState(
        isLoading = false,
        detailsState = DetailsState(
            name = product.name,
            image = product.image,
            price = product.price,
            discount = product.discount,
            hasDiscount = product.hasDiscount
        ),
        hasError = false,
        errorProvider = { "" }
    )
    DetailsScreen(
        state = state,
        onError = {}
    )
}
