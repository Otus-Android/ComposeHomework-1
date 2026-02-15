package ru.otus.marketsample.products.feature

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    state: ProductsScreenState,
    onRefresh: () -> Unit,
    onItemClicked: (String) -> Unit,
    onErrorShown: () -> Unit
) {
    val context = LocalContext.current

    if (state.hasError) {
        LaunchedEffect(state.hasError) {
            Toast.makeText(context, state.errorProvider(context), Toast.LENGTH_SHORT).show()
            onErrorShown()
        }
    }

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.productListState) { product ->
                    ProductCard(
                        product = product,
                        onItemClicked = onItemClicked
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                }
            }

            if (state.isLoading && state.productListState.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
