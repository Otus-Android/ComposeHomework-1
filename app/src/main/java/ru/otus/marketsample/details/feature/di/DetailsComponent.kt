package ru.otus.marketsample.details.feature.di

import dagger.Component
import ru.otus.common.data.products.ProductRepository
import ru.otus.common.di.FeatureScope

@FeatureScope
@Component(dependencies = [DetailsComponentDependencies::class])
interface DetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: DetailsComponentDependencies
        ): DetailsComponent
    }
}

interface DetailsComponentDependencies {
    fun getProductRepository(): ProductRepository
}