package ru.otus.marketsample.promo

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.otus.marketsample.products.feature.ui.CircularProgressBar
import ru.otus.marketsample.promo.feature.PromoListViewModel
import marketsample.promo.feature.PromoState
import ru.otus.marketsample.promo.feature.ui.PromoCard

@Composable
fun PromoScreen(
    modifier: Modifier,
    viewModel: PromoListViewModel = viewModel<PromoListViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val isLoading = rememberSwipeRefreshState(isRefreshing = state.isLoading)
    SwipeRefresh(
        state = isLoading,
        onRefresh = viewModel::refresh,
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> CircularProgressBar(Modifier)
            state.hasError -> Toast.makeText(
                LocalContext.current, "Error wile loading data", Toast.LENGTH_SHORT
            ).show()

            else -> PromoList(Modifier, state.promoListState)
        }
    }
}

@Composable
fun PromoList(modifier: Modifier, list: List<PromoState>) = LazyColumn(modifier) {
    items(list) { item ->
        PromoCard(item, Modifier)
    }
}