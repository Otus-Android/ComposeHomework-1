package ru.otus.marketsample.promo.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.coroutines.launch
import ru.otus.marketsample.navigation.SnackbarController
import ru.otus.marketsample.navigation.SnackbarEvent
import theme.MarketSampleTheme

@Composable
fun PromoListScreen(
    state: PromoScreenState,
    errorHasShown: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.hasError -> {
                LaunchedEffect(Unit) {
                    scope.launch {
                        SnackbarController.sendEvent(
                            SnackbarEvent(state.errorProvider(context))
                        )
                    }
                }
                errorHasShown()
            }

            else -> {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                ) {
                    LazyColumn {
                        items(state.promoListState, { it.id }) { promoState ->
                            PromoListItem(promoState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun PromoListScreenPreview() {
    MarketSampleTheme {
        PromoListScreen(
            state = PromoScreenState(
                isLoading = false,
                promoListState = listOf(
                    PromoState(
                        id = "1",
                        name = "Name",
                        description = "Bla bla bla",
                        image = "image",
                    )
                ),
                hasError = false,
            ),
            errorHasShown = { },
            isRefreshing = false,
            onRefresh = { },
        )
    }
}
