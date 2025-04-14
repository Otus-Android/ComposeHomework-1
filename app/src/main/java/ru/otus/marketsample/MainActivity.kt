package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.databinding.ActivityMainBinding
import ru.otus.marketsample.navigation.MarketSampleNavGraph

const val COMPOSE_FEATURE_TOGGLE = true
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!COMPOSE_FEATURE_TOGGLE) {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        } else {
            setContent {
                val navController = rememberNavController()
                MarketSampleNavGraph(
                    navController = navController
                )
            }
        }
    }
}