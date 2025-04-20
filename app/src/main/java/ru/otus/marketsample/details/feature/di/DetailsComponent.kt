package ru.otus.marketsample.details.feature.di

import dagger.BindsInstance
import dagger.Component
import ru.otus.common.data.products.ProductRepository
import ru.otus.marketsample.details.feature.view.DetailsFragment
import ru.otus.common.di.FeatureScope
import ru.otus.marketsample.details.feature.DetailsViewModel
import javax.inject.Named

@FeatureScope
@Component(dependencies = [DetailsComponentDependencies::class])
interface DetailsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: DetailsComponentDependencies,
            @BindsInstance @Named("productId") productId: String,
        ): DetailsComponent
    }

    fun inject(detailsFragment: DetailsFragment)

    fun viewModel(): DetailsViewModel
}

interface DetailsComponentDependencies {
    fun getProductRepository(): ProductRepository
}
