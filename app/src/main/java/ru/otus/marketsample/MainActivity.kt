package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.otus.marketsample.common.ObserveAsEvents
import ru.otus.marketsample.navigation.BottomBar
import ru.otus.marketsample.navigation.MarketSampleNavHost
import ru.otus.marketsample.navigation.SnackbarController
import ru.otus.marketsample.navigation.rememberBarVisibility
import ru.otus.marketsample.navigation.rememberNavigationState
import ru.otus.marketsample.navigation.rememberSnackbarHostState
import theme.MarketSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketSampleTheme {
                val bottomBarState = rememberBarVisibility()
                val navigationState = rememberNavigationState()
                val snackbarHostState = rememberSnackbarHostState()
                val scope = rememberCoroutineScope()

                ObserveAsEvents(
                    flow = SnackbarController.events,
                    snackbarHostState,
                ) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short,
                        )
                    }
                }

                Scaffold(
                    bottomBar = {
                        Box(
                            modifier = Modifier.height(100.dp)
                        ) {
                            Crossfade(
                                targetState = bottomBarState.isVisible,
                                animationSpec = tween(200),
                            ) { isBottomBarVisible ->
                                when (isBottomBarVisible) {
                                    true -> BottomBar(navigationState)
                                    false -> Spacer(modifier = Modifier)
                                }
                            }
                        }
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                        )
                    },
                ) {
                    MarketSampleNavHost(
                        navigationState,
                        bottomBarState,
                        Modifier.padding(it),
                    )
                }
            }
        }
    }
}