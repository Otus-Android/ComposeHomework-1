package ru.otus.marketsample

import android.app.Application
import ru.otus.common.di.Dependencies
import ru.otus.common.di.DependenciesProvider
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.di.DaggerAppComponent

class MarketSampleApp : Application(), DependenciesProvider {
    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun getDependencies(): Dependencies = appComponent
}