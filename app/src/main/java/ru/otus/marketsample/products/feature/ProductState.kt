package ru.otus.marketsample.products.feature

import android.content.Context
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

typealias ErrorProvider = (Context) -> String

data class ProductsScreenState(
    val isLoading: Boolean = false,
    val productListState: ImmutableList<ProductState> = persistentListOf(),
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
