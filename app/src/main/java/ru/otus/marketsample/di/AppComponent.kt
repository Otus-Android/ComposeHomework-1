package ru.otus.marketsample.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.otus.marketsample.MainActivity


@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun getViewModelComponentFactory(): DetailsViewModelComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}