package ru.otus.marketsample.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.promo.feature.PromoListViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @StringKey("ProductListViewModel")
    @Binds
    fun bindProductsListViewModel(impl: ProductListViewModel): ViewModel

    @IntoMap
    @StringKey("PromoListViewModel")
    @Binds
    fun bindPromoListViewModel(impl: PromoListViewModel): ViewModel
}