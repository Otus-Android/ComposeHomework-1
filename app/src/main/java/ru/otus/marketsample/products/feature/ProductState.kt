package ru.otus.marketsample.products.feature

import android.content.Context
import androidx.compose.runtime.Stable

typealias ErrorProvider = (Context) -> String

@Stable
data class ProductsScreenState(
    val isLoading: Boolean = false,
    val productListState: List<ProductState> = emptyList(),
    val hasError: Boolean = false,
    val errorProvider: ErrorProvider = { "" },
)

data class ProductState(
    val id: String,
    val name: String,
    val image: String,
    val price: String,
    val hasDiscount: Boolean,
    val discount: String,
)
