package ru.otus.marketsample

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.common.Screen
import ru.otus.marketsample.details.feature.DetailsScreen
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.products.feature.ProductsScreen
import ru.otus.marketsample.promo.feature.PromoScreen

@Composable
fun MainContent(appComponent: AppComponent) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(Screen.Products, Screen.Promo)
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Details.route) {
                BottomBar(
                    navController = navController,
                    items = items,
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Products.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Products.route) {
            ProductsScreen(
                    appComponent = appComponent,
                    navController = navController,
                )
            }
            composable(Screen.Promo.route) {
                PromoScreen(appComponent)
            }
            composable(Screen.Details.route) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString(Screen.Details.PRODUCT_ID) ?: ""
                DetailsScreen(
                    appComponent = appComponent,
                    productId = productId
                )
            }
        }
    }
}
@Composable
fun BottomBar(
    items: List<Screen>,
    navController: NavController,
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        ImageVector.vectorResource(
                            screen.iconRes
                        ),
                        modifier = Modifier.size(24.dp),
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(screen.titleRes)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = ru.otus.common.ui.R.color.purple_500),
                    selectedTextColor = colorResource(id = ru.otus.common.ui.R.color.purple_500),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}