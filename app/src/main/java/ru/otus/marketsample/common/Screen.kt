package ru.otus.marketsample.common

import ru.otus.marketsample.R

sealed class Screen(val route: String, val titleRes: Int, val iconRes: Int) {
    object Products : Screen("products_fragment", R.string.title_products, ru.otus.common.ui.R.drawable.ic_list)
    object Promo : Screen("promo_fragment", R.string.title_promo, ru.otus.common.ui.R.drawable.ic_discount)
    object Details : Screen("details/{productId}", 0, 0) {
        const val PRODUCT_ID = "productId"
        fun createRoute(productId: String) = "details/$productId"
    }
}