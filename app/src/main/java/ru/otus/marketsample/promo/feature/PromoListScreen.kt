package ru.otus.marketsample.promo.feature

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreen(
    viewModel: PromoListViewModel,
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
            if (this@with.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = promoListState,
                        key = { product -> product.id }
                    ) { promo ->
                        PromoItem(
                            promo = promo
                        )
                    }
                }
            }
        }
    }
}