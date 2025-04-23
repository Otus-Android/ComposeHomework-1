package ru.otus.marketsample.promo.feature

import android.content.Context
import androidx.compose.runtime.Stable

typealias ErrorProvider = (Context) -> String

@Stable
data class PromoScreenState(
    val isLoading: Boolean = false,
    val promoListState: List<PromoState> = emptyList(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
)

data class PromoState(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
)