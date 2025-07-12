package ru.otus.marketsample

import android.app.Application
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.di.DaggerAppComponent

class MarketSampleApp : Application() {
    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)
}
