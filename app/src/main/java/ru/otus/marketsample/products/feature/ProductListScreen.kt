package ru.otus.marketsample.products.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.coroutines.launch
import ru.otus.marketsample.navigation.SnackbarController
import ru.otus.marketsample.navigation.SnackbarEvent
import theme.MarketSampleTheme

@Composable
fun ProductListScreen(
    state: ProductsScreenState,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    errorHasShown: () -> Unit,
    onItemClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.hasError -> {
                LaunchedEffect(Unit) {
                    scope.launch {
                        SnackbarController.sendEvent(
                            SnackbarEvent(state.errorProvider(context))
                        )
                    }
                }
                errorHasShown()
            }

            else -> {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                ) {
                    LazyColumn {
                        items(state.productListState, { it.id }) { productState ->
                            ProductListItem(
                                productState = productState,
                                onItemClick = onItemClick,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun ProductListScreenPreview() {
    MarketSampleTheme {
        ProductListScreen(
            state = ProductsScreenState(
                isLoading = false,
                productListState = List(10) {
                    ProductState(
                        id = it.toString(),
                        name = "Name $it",
                        image = "image",
                        price = "2000 руб",
                        hasDiscount = true,
                        discount = "-20%",
                    )
                },
                hasError = false,
                errorProvider = { "" },
            ),
            isRefreshing = false,
            onRefresh = { },
            onItemClick = { },
            errorHasShown = {},
        )
    }
}
