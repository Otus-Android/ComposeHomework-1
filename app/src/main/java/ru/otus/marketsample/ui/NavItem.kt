package ru.otus.marketsample.ui

import ru.otus.marketsample.navigate.Screen

sealed class NavItem(
    val screen: Screen,
    val title: String,
    val drawableId: Int
) {
    class ProductsItemNavigate(title: String, drawableId: Int) : NavItem(
        screen = Screen.Products,
        title = title,
        drawableId = drawableId
    )

    class PromoItemNavigate(title: String, drawableId: Int) : NavItem(
        screen = Screen.Promo,
        title = title,
        drawableId = drawableId
    )
}