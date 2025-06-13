package ru.otus.marketsample.products.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.otus.marketsample.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProductListComposeScreen(
    state: ProductsScreenState,
    onRefresh: () -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val pullToRefreshState = rememberPullToRefreshState()
    val scrollState = rememberScrollState()

    PullToRefreshBox(
        modifier = modifier
            .fillMaxSize(),
        state = pullToRefreshState,
        isRefreshing = state.isLoading,
        onRefresh = onRefresh
    ) {
        if (state.productListState.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .verticalScroll(scrollState)
            ) {
                state.productListState.forEach { item ->
                    ProductItemCompose(item = item, onClick = { onItemClick(item.id) })
                }
            }
        } else if (!state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(id = R.string.error_wile_loading_data))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductListComposeScreenPreview() {
    val sampleProducts = listOf(
        ProductState(
            id = "228",
            name = "Вафли с жидким шоколадом",
            image = "https://otus-android.github.io/static/compose-hw1/img/product05.webp",
            price = "228,00 руб.",
            hasDiscount = true,
            discount = "15%"
        ),
        ProductState(
            id = "228",
            name = "Вафли с жидким шоколадом",
            image = "https://otus-android.github.io/static/compose-hw1/img/product05.webp",
            price = "228,00 руб.",
            hasDiscount = true,
            discount = "15%"
        )
    )

    val state = ProductsScreenState(
        isLoading = false,
        productListState = sampleProducts,
        hasError = false,
        errorProvider = { "" },
    )

    ProductListComposeScreen(state = state, onRefresh = {}, onItemClick = {})
}