package ru.otus.marketsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.otus.marketsample.details.feature.compose.ProductDetailsScreen

@Composable
fun MarketSampleNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) = NavHost(
    navController = navController,
    modifier = modifier,
    startDestination = MarketSampleScreens.MAIN_SCREEN.name,
) {
    composable(
        route = MarketSampleScreens.MAIN_SCREEN.name
    ) {
        MainScreen(navController)
    }

    composable(
        route = MarketSampleScreens.PRODUCT_DETAILS.name,
    ) {
        ProductDetailsScreen(navController)
    }
}