package ru.otus.marketsample.navigation.bottomBar.domain

import ru.otus.common.ui.R
import ru.otus.marketsample.navigation.Route

enum class NavigationItem(
    val route: Route,
    val title: String,
    val iconResId: Int,
) {
    PRODUCTS(Route.Products, "Products", R.drawable.ic_list),
    PROMO(Route.Promo, "Promo", R.drawable.ic_discount)
}