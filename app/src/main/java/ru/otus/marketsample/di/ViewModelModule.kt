package ru.otus.marketsample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.promo.feature.PromoListViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(impl: ViewModelsFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    fun bindProductListViewModel(impl: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PromoListViewModel::class)
    fun bindPromoListViewModel(impl: PromoListViewModel): ViewModel
}