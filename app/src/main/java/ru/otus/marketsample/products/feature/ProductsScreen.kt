package ru.otus.marketsample.products.feature

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.otus.marketsample.navigation.Routes
import ru.otus.marketsample.products.feature.ui.CircularProgressBar
import ru.otus.marketsample.products.feature.ui.ProductCard

@Composable
fun ProductsScreen(
    modifier: Modifier,
    viewModel: ProductListViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val isLoading = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    SwipeRefresh(
        onRefresh = viewModel::refresh,
        modifier = modifier.fillMaxSize(),
        state = isLoading
    ) {
        when {
            state.isLoading -> CircularProgressBar(Modifier)
            state.hasError -> Toast.makeText(
                LocalContext.current, "Error wile loading data", Toast.LENGTH_SHORT
            ).show()

            else -> ProductList(
                Modifier, state.productListState, { id ->
                    navController.navigate(Routes.Details.route + "/$id")
                }
            )
        }
    }
}

@Composable
fun ProductList(modifier: Modifier, list: List<ProductState>, callback: (id: String) -> Unit) =
    LazyColumn(modifier) {
        items(list) { item ->
            ProductCard(item, Modifier.clickable { callback.invoke(item.id) }
            )
        }
    }