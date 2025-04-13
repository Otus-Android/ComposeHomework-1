package ru.otus.marketsample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

@Composable
fun MarketSampleNavGraph(
    navigationState: NavigationState,
) {
    NavHost(navController = navigationState.navHostController, startDestination = Route.Products) {
        navigation<Route.Products>(startDestination = ProductsRoute.List) {
            composable<ProductsRoute.List> {  }
            composable<ProductsRoute.Details> {  }
        }

        composable<Route.Promo> {

        }
    }
}