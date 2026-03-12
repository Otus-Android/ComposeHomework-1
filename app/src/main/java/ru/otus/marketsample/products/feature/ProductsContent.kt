package ru.otus.marketsample.products.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsContent(
    isLoading: Boolean,
    products: List<ProductState>,
    onRefresh: () -> Unit,
    onItemClicked: (String) -> Unit,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = isLoading,
        onRefresh = {
            onRefresh()
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = products,
                key = { it.id },
            ) { product ->
                ProductItem(
                    state = product,
                    onItemClicked = onItemClicked,
                )
            }
        }
    }
}