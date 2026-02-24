package ru.otus.marketsample.products.feature.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.otus.marketsample.products.feature.ProductsScreenState

@Composable
fun ProductListScreen(
    state: ProductsScreenState,
    onRefresh: () -> Unit,
    onProductClick: (String) -> Unit
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = state.productListState,
                key = { it.id }
            ) { product ->
                ProductItem(product = product, onProductClick = onProductClick)
            }
        }
    }
}