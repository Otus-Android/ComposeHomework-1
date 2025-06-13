package ru.otus.marketsample.promo.feature

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
import androidx.compose.ui.unit.dp
import ru.otus.marketsample.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PromoListComposeScreen(
    state: PromoScreenState,
    onRefresh: () -> Unit,
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
        if (state.promoListState.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .verticalScroll(scrollState)
            ) {
                state.promoListState.forEach { item ->
                    PromoItemCompose(item)
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
