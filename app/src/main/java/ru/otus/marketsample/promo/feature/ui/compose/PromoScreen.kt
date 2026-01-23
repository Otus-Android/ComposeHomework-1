package ru.otus.marketsample.promo.feature.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.otus.marketsample.promo.feature.ui.PromoListViewModel
import ru.otus.marketsample.promo.feature.ui.PromoScreenState
import ru.otus.marketsample.promo.feature.ui.PromoState

@Composable
fun PromoScreenRoute(
    viewModel: PromoListViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PromoScreen(
        state = state,
        onRefresh = viewModel::refresh,
        onError = viewModel::errorHasShown
    )
}

@Composable
private fun PromoScreen(
    state: PromoScreenState,
    onRefresh: () -> Unit,
    onError: () -> Unit
) {
    val context = LocalContext.current
    if (state.hasError) {
        Toast.makeText(
            context,
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
        onError()
    }
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.promoListState.size) { index ->
                PromoCard(state.promoListState[index])
            }
        }
    }
}

@Preview
@Composable
private fun PromoScreenPreview() {
    val promos = listOf(
        PromoState(
            id = "1",
            name = "Летний Sale",
            description = "Скидки до 50% на все солнцезащитные очки только до конца недели.",
            image = "https://example.com/promo1.jpg"
        ),
        PromoState(
            id = "2",
            name = "Кешбэк 15%",
            description = "Возвращаем часть средств при оплате картами партнеров в категории «Еда».",
            image = "https://example.com/promo2.jpg"
        ),
        PromoState(
            id = "3",
            name = "Новая коллекция",
            description = "Посмотрите наши последние поступления осеннего сезона уже сегодня.",
            image = "https://example.com/promo3.jpg"
        ),
        PromoState(
            id = "4",
            name = "Бесплатная доставка",
            description = "При заказе от 3000 рублей доставим ваш товар прямо до двери бесплатно.",
            image = "https://example.com/promo4.jpg"
        )
    )
    val state = PromoScreenState(
        promoListState = promos
    )
    PromoScreen(
        state = state,
        onRefresh = {},
        onError = {}
    )
}