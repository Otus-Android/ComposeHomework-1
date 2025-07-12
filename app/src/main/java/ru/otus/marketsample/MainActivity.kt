package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import ru.otus.marketsample.ui.MainApplicationScreen
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (this.application as MarketSampleApp).appComponent

        appComponent.inject(this)

        enableEdgeToEdge()
        setContent {
            MainApplicationScreen(
                modifier = Modifier.fillMaxSize(),
                factory = factory,
            )
        }
    }
}