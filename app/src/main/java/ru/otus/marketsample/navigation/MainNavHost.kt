package ru.otus.marketsample.navigation

import kotlinx.serialization.Serializable
import ru.otus.marketsample.R
import ru.otus.common.ui.R as RUi

interface MainDestination {
    val labelRes: Int
    val iconRes: Int
}

@Serializable
data class
Products(
    override val labelRes: Int = R.string.title_products,
    override val iconRes: Int = RUi.drawable.ic_list,
) : MainDestination

@Serializable
data class Promo(
    override val labelRes: Int = R.string.title_promo,
    override val iconRes: Int = RUi.drawable.ic_discount,
) : MainDestination


@Serializable
object Product
