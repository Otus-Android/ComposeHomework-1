package ru.otus.marketsample.products.feature.ui.compose

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.marketsample.products.feature.ui.ProductListViewModel
import ru.otus.marketsample.products.feature.ui.ProductState
import ru.otus.marketsample.products.feature.ui.ProductsScreenState
import ru.otus.marketsample.products.feature.ui.compose.components.ProductCard

@Composable
fun ProductListRoute(
    viewModel: ProductListViewModel,
    navigateToDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductListScreen(
        state = state,
        navigateToDetails = navigateToDetails,
        onRefresh = viewModel::refresh
    )
}

@Composable
private fun ProductListScreen(
    state: ProductsScreenState,
    navigateToDetails: (String) -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    if (state.hasError) {
        Toast.makeText(
            context,
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
    }
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(state.productListState.size) { index ->
                ProductCard(
                    state = state.productListState[index],
                    navigateToDetails = navigateToDetails
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductsScreenPreview() {
    val previewState = ProductsScreenState(
        isLoading = false,
        productListState = listOf(
            ProductState(
                id = "1",
                name = "Персиковый кранч",
                image = "https://example.com/food_peach_crunch.jpg",
                price = "320,00 руб",
                hasDiscount = true,
                discount = "10%"
            ),
            ProductState(
                id = "2",
                name = "Черничные блинчики",
                image = "https://example.com/food_blueberry_pancakes.jpg",
                price = "280,00 руб",
                hasDiscount = false,
                discount = ""
            ),
            ProductState(
                id = "3",
                name = "Вафли с жидким шоколадом",
                image = "https://example.com/food_choco_waffles.jpg",
                price = "250,00 руб",
                hasDiscount = true,
                discount = "7%"
            ),
            ProductState(
                id = "4",
                name = "Фруктовый мусс",
                image = "https://example.com/food_fruit_mousse.jpg",
                price = "300,00 руб",
                hasDiscount = false,
                discount = ""
            )
        ),
        hasError = false,
        errorProvider = { "" }
    )

    ProductListScreen(state = previewState, onRefresh = {}, navigateToDetails = {})
}
