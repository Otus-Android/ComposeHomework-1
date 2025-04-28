package ru.otus.marketsample.products.feature.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.otus.marketsample.common.ErrorHandler
import ru.otus.marketsample.navigation.MarketSampleScreens
import ru.otus.marketsample.products.feature.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProductListViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ErrorHandler(state.hasError) { viewModel.errorHasShown() }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = state.isLoading,
        onRefresh = viewModel::refresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.productListState, key = { it.id }) { item ->
                ProductItem(
                    state = item,
                    onClick = {
                        navController.navigate(
                            MarketSampleScreens.PRODUCT_DETAILS.name +
                                    "/${item.id}"
                        )
                    }
                )
            }
        }
    }
}