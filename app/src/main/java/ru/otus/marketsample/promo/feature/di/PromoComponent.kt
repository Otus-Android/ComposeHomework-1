package ru.otus.marketsample.promo.feature.di

import dagger.Component
import ru.otus.common.data.promo.PromoRepository
import ru.otus.common.di.FeatureScope
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.view.PromoListFragment

@FeatureScope
@Component(dependencies = [PromoComponentDependencies::class])
interface PromoComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: PromoComponentDependencies): PromoComponent
    }

    fun inject(productFragment: PromoListFragment)
    fun viewModel(): PromoListViewModel
}

interface PromoComponentDependencies {
    fun getPromoRepository(): PromoRepository
}
