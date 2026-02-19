package ru.otus.marketsample

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductsScreen
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.products.feature.di.ProductListComponentDependencies
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromosScreen
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import ru.otus.marketsample.promo.feature.di.PromoComponentDependencies

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOf(ScreenRoute.Products, ScreenRoute.Promos).forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(screen.icon, contentDescription = screen.title)
                        },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Products.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenRoute.Products.route) {
                ProductListScreenContent()
            }
            composable(ScreenRoute.Promos.route) {
                PromoListScreenContent()
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun ProductListScreenContent() {
    val dependencies = LocalContext.current.findDependencies<ProductListComponentDependencies>()
    val component = DaggerProductListComponent.factory().create(dependencies)
    val viewModel: ProductListViewModel = viewModel(factory = component.viewModelFactory())
    val state by viewModel.state.collectAsState()
    ProductsScreen(
        state = state,
        onRefresh = { viewModel.refresh() },
        onItemClicked = { productId ->
//            requireActivity().findNavController(R.id.nav_host_activity_main)
//                .navigate(
//                    resId = R.id.action_main_to_details,
//                    args = bundleOf("productId" to productId),
//                )
        },
        onErrorShown = { viewModel.errorHasShown() }
    )
}

@Composable
fun PromoListScreenContent() {
    val dependencies = LocalContext.current.findDependencies<PromoComponentDependencies>()
    val component = DaggerPromoComponent.factory().create(dependencies)
    val viewModel: PromoListViewModel = viewModel(factory = component.viewModelFactory())
    val state by viewModel.state.collectAsState()

    PromosScreen(
        state = state,
        onRefresh = { viewModel.refresh() },
        onItemClicked = { productId ->
//            requireActivity().findNavController(R.id.nav_host_activity_main)
//                .navigate(
//                    resId = R.id.action_main_to_details,
//                    args = bundleOf("productId" to productId),
//                )
        },
        onErrorShown = { viewModel.errorHasShown() }
    )
}