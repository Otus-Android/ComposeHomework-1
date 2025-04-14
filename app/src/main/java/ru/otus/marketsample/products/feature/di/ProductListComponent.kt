package ru.otus.marketsample.products.feature.di

import dagger.Component
import ru.otus.common.data.products.ProductRepository
import ru.otus.common.data.promo.PromoRepository
import ru.otus.common.di.FeatureScope
import ru.otus.marketsample.products.feature.ProductListFragment
import ru.otus.marketsample.products.feature.ProductListViewModelFactory

@FeatureScope
@Component(dependencies = [ProductListComponentDependencies::class])
interface ProductListComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ProductListComponentDependencies,
        ): ProductListComponent
    }

    fun inject(productListFragment: ProductListFragment)

    fun getProductListViewModelFactory(): ProductListViewModelFactory
}

interface ProductListComponentDependencies {
    fun getPromoRepository(): PromoRepository
    fun getProductRepository(): ProductRepository
}