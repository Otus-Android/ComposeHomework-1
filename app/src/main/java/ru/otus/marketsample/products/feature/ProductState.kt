package ru.otus.marketsample.products.feature

import android.content.Context
import androidx.compose.runtime.Immutable

typealias ErrorProvider = (Context) -> String

@Immutable
data class ProductsScreenState(
    val isLoading: Boolean = false,
    val productListState: List<ProductState> = emptyList(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
    val isRefreshing: Boolean = false
)

data class ProductState(
    val id: String,
    val name: String,
    val image: String,
    val price: String,
    val hasDiscount: Boolean,
    val discount: String,
)
