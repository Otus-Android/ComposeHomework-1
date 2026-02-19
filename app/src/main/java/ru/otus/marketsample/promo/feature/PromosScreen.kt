package ru.otus.marketsample.promo.feature

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromosScreen(
    state: PromoScreenState,
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
                items(state.promoListState) { promo ->
                    PromoCard(
                        promo = promo,
                        onItemClicked = onItemClicked
                    )
                }
            }

            if (state.isLoading && state.promoListState.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
