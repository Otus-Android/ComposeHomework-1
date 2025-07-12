package ru.otus.marketsample.ui.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.otus.marketsample.ViewModelFactory
import ru.otus.marketsample.navigate.Screen
import ru.otus.marketsample.products.feature.ProductListViewModel

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    factory: ViewModelFactory,
    onClick: (screen: Screen, id: String) -> Unit
) {
    val viewModel: ProductListViewModel = viewModel(factory = factory)
    val state = viewModel.state.collectAsState().value
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = state.productListState, key = { it.id }) { promoState ->
                ProductItem(
                    modifier = Modifier.fillMaxWidth(),
                    promoState,
                    onClick = {
                        onClick(
                            Screen.Details,
                            promoState.id
                        )
                    }
                )
            }
        }
        if (state.isLoading)
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
            )
    }
}
