package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.navigation.MarketSampleNavGraph

val LocalSnackbar = staticCompositionLocalOf<SnackbarHostState> {
    error("CompositionLocal LocalSnackbar not present")
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                content = { paddingValues ->
                    CompositionLocalProvider(LocalSnackbar provides snackbarHostState) {
                        MarketSampleNavGraph(
                            modifier = Modifier.padding(paddingValues),
                            navController = navController,
                        )
                    }
                }
            )
        }
    }
}