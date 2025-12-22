package ru.otus.marketsample

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.otus.common.di.Dependencies
import ru.otus.common.di.DependenciesProvider
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.di.DaggerAppComponent

class MarketSampleApp: Application(), DependenciesProvider {
    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun getDependencies(): Dependencies {
        return appComponent
    }
}

@Composable
fun getApplicationComponent(): AppComponent {
    return (LocalContext.current.applicationContext as MarketSampleApp).appComponent
}
