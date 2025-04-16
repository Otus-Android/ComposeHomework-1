package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.otus.marketsample.navigation.MarketSampleNavGraph
import ru.otus.marketsample.navigation.bottomBar.BottomBar
import ru.otus.marketsample.navigation.rememberNavigationState
import theme.MarketSampleTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketSampleTheme {
                val navigationState = rememberNavigationState()

                Scaffold(
                    bottomBar = {
                        BottomBar(navigationState)
                    }
                ) {
                    MarketSampleNavGraph(navigationState, Modifier.padding(it))
                }
            }
        }
    }
}