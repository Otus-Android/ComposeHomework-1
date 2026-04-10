package ru.otus.marketsample
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.otus.marketsample.details.feature.DetailsScreen
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.details.feature.di.DetailsComponentDependencies
import ru.otus.marketsample.products.feature.ProductListScreen
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.products.feature.di.ProductListComponentDependencies
import ru.otus.marketsample.promo.feature.PromoListScreen
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import ru.otus.marketsample.promo.feature.di.PromoComponentDependencies
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                NavigationBarItem(
                    icon = { Icon(painterResource(ru.otus.common.ui.R.drawable.ic_list), tint = null, contentDescription = "Продукты") },
                    label = { Text("Продукты") },
                    selected = currentDestination?.hierarchy?.any { it.route == "products" } == true,
                    onClick = {
                        navController.navigate("products") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(ru.otus.common.ui.R.drawable.ic_discount), tint = null, contentDescription = "Акции") },
                    label = { Text("Акции") },
                    selected = currentDestination?.hierarchy?.any { it.route == "promo" } == true,
                    onClick = {
                        navController.navigate("promo") {
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
    ) { innerPadding ->
        val context = LocalContext.current
        val dependencies = (context.applicationContext as MarketSampleApp).getDependencies()
        NavHost(
            navController = navController,
            startDestination = "products",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("products") {
                val component = remember {
                    DaggerProductListComponent.factory().create(dependencies as ProductListComponentDependencies)
                }
                val factory = component.getViewModelFactory()
                val viewModel: ProductListViewModel = viewModel(factory = factory)
                val state by viewModel.state.collectAsState()
                ProductListScreen(
                    state = state,
                    onRefresh = { viewModel.refresh() },
                    onItemClick = { productId ->
                        navController.navigate("details/$productId")
                    }
                )
            }
            composable("promo") {
                val component = remember {
                    DaggerPromoComponent.factory().create(dependencies as PromoComponentDependencies)
                }
                val factory = component.getViewModelFactory()
                val viewModel: PromoListViewModel = viewModel(factory = factory)
                val state by viewModel.state.collectAsState()
                PromoListScreen(
                    state = state,
                    onRefresh = { viewModel.refresh() }
                )
            }
            composable(
                route = "details/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: return@composable

                val component = remember(productId) {
                    DaggerDetailsComponent.factory().create(dependencies as DetailsComponentDependencies, productId)
                }
                val factory = component.getViewModelFactory()
                val viewModel: DetailsViewModel = viewModel(factory = factory)
                val state by viewModel.state.collectAsState()
                DetailsScreen(state = state)
            }
        }
    }
}
