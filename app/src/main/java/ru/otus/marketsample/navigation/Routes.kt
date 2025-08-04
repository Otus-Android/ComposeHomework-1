package ru.otus.marketsample.navigation

sealed class Routes(val route: String) {
    data object Products : Routes("products")
    data object Promo : Routes("promo")
    data object Details : Routes("details")
}