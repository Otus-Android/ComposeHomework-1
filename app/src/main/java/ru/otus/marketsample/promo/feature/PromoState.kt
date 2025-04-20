package ru.otus.marketsample.promo.feature

import android.content.Context
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

typealias ErrorProvider = (Context) -> String

data class PromoScreenState(
    val isLoading: Boolean = false,
    val promoListState: ImmutableList<PromoState> = persistentListOf(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
)

data class PromoState(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
)