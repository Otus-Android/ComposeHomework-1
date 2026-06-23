package ru.otus.marketsample.products.feature

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    with(state) {

        LaunchedEffect(key1 = hasError) {
            if (hasError) {
                Toast.makeText(
                    context,
                    "Error while loading data",
                    Toast.LENGTH_SHORT
                ).show()

                viewModel.errorHasShown()
            }
        }

        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = {},
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (this@with.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = productListState,
                            key = { product -> product.id }
                        ) { product ->
                            ProductItem(
                                product = product,
                                onClick = onClick
                            )
                        }
                    }
                }
            }
        }
    }

}