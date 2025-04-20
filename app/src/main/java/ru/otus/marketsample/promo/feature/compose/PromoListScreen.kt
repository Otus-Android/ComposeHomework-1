package ru.otus.marketsample.promo.feature.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.marketsample.common.ErrorHandler
import ru.otus.marketsample.common.Loader
import ru.otus.marketsample.promo.feature.PromoListViewModel

@Composable
fun PromoListScreen(
    viewModel: PromoListViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ErrorHandler(state.hasError) { viewModel.errorHasShown() }

    Loader(isLoading = state.isLoading) {
        LazyColumn {
            items(state.promoListState, key = { it.id }) { item ->
                PromoItem(item)
            }
        }
    }
}