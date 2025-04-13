package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ru.otus.marketsample.databinding.ActivityMainBinding
import theme.MarketSampleTheme

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketSampleTheme {

            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}