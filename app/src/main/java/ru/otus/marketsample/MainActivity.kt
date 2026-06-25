package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.navigation.CreateGraph

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val currentRoute = navController
                .currentBackStackEntryAsState()
                .value
                ?.destination
                ?.route

            Scaffold(
                bottomBar = {
                    if (currentRoute != "details/{productId}") {
                        BottomBar(navController = navController)
                    }
                }
            ) { padding ->
                CreateGraph(
                    navController = navController,
                    appComponent = (application as MarketSampleApp).appComponent,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}