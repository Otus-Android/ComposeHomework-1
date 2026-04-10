package ru.otus.marketsample.details.feature

import android.content.Context
import androidx.compose.runtime.Immutable

typealias ErrorProvider = (Context) -> String

@Immutable
data class DetailsScreenState(
    val isLoading: Boolean = false,
    val detailsState: DetailsState = DetailsState(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
)

data class DetailsState(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val price: String = "",
    val hasDiscount: Boolean = false,
    val discount: String = "",
)
