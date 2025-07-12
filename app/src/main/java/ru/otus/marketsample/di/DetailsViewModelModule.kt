package ru.otus.marketsample.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.otus.marketsample.details.feature.DetailsViewModel


@Module
interface DetailsViewModelModule {

    @IntoMap
    @StringKey("DetailsViewModel")
    @Binds
    fun bindDetailsViewModelViewModel(impl: DetailsViewModel): ViewModel
}