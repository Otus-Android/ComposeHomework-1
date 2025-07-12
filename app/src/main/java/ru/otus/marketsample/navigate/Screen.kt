package ru.otus.marketsample.navigate

sealed class Screen(val route: String) {
    object Products : Screen(PRODUCTS)
    object Promo : Screen(PROMO)
    object Details : Screen(DETAILS) {
        private const val DETAILS_FOR_ARGS = "details"
        fun getRouteWithArgs(id: String) = "$DETAILS_FOR_ARGS/$id"
    }

    companion object {
        private const val PRODUCTS = "products"
        private const val PROMO = "promo"
        const val KEY_PRODUCT_ID = "productId"
        private const val DETAILS = "details/{$KEY_PRODUCT_ID}"
    }
}