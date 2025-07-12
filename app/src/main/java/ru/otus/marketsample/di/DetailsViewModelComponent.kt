package ru.otus.marketsample.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.otus.marketsample.ViewModelFactory
import javax.inject.Named

@Subcomponent(
    modules = [DetailsViewModelModule::class]
)
interface DetailsViewModelComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named("productId") productId: String,
        ): DetailsViewModelComponent
    }
}