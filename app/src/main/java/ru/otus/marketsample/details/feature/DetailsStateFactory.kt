package ru.otus.marketsample.details.feature

import ru.otus.common.formatters.PriceFormatter
import ru.otus.marketsample.details.domain.ProductDetails
import javax.inject.Inject

class DetailsStateFactory @Inject constructor(
    private val priceFormatter: PriceFormatter,
) {
    fun create(productDetails: ProductDetails): DetailsState {
        return DetailsState(
            id = productDetails.id,
            name = productDetails.name,
            image = productDetails.image,
            price = productDetails.price.let(priceFormatter::format),
            hasDiscount = false,
        )
    }
}