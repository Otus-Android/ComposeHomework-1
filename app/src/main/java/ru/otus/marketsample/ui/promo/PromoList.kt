package ru.otus.marketsample.ui.promo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.otus.marketsample.promo.feature.PromoState

@Composable
fun PromoList(
    modifier: Modifier = Modifier,
    promoStateList: List<PromoState>
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = promoStateList, key = { it.id }) { promoState ->
                PromoItem(
                    modifier = Modifier.fillMaxWidth(),
                    promoState = promoState,
                )
            }
        }
    }
}
