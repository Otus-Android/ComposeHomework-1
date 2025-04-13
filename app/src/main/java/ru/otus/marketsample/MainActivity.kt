package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.otus.marketsample.databinding.ActivityMainBinding
import ru.otus.marketsample.navigation.MarketSampleNavGraph
import ru.otus.marketsample.navigation.rememberNavigationState
import theme.MarketSampleTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketSampleTheme {
                val navigationState = rememberNavigationState()

                MarketSampleNavGraph(navigationState)
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}