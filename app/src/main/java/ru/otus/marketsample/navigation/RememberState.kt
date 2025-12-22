package ru.otus.marketsample.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class NavigationState(val navHostController: NavHostController) {
    fun navigateInBottomBarTo(route: Route) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToProductDetails(id: String) {
        navHostController.navigate(ProductsRoute.Details(id))
    }
}

@Composable
fun rememberNavigationState(controller: NavHostController = rememberNavController()) =
    remember { NavigationState(controller) }


class BarVisibilityState(defaultVisibility: Boolean = true) {
    private val _isVisible = mutableStateOf(defaultVisibility)
    val isVisible: Boolean by _isVisible

    fun hide() {
        _isVisible.value = false
    }

    fun show() {
        _isVisible.value = true
    }
}

@Composable
fun rememberBarVisibility(): BarVisibilityState = remember { BarVisibilityState() }


data class SnackbarEvent(val message: String)
object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}

@Composable
fun rememberSnackbarHostState(): SnackbarHostState = remember { SnackbarHostState() }
