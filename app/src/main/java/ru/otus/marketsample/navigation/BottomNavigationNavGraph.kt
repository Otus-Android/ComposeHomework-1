package ru.otus.marketsample.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.otus.marketsample.products.feature.compose.ProductsScreen
import ru.otus.marketsample.promo.feature.compose.PromoScreen

@Composable
fun BottomNavigationNavGraph(
    hostNavController: NavHostController,
    bottomNavigationNavController: NavHostController,
    paddingValues: PaddingValues,
) = NavHost(
    navController = bottomNavigationNavController,
    modifier = Modifier.padding(paddingValues),
    startDestination = MarketSampleScreens.PRODUCTS_LIST.name,
) {
    composable(
        route = MarketSampleScreens.PRODUCTS_LIST.name,
    ) {
        ProductsScreen(hostNavController)
    }

    composable(
        route = MarketSampleScreens.PROMO_LIST.name,
    ) {
        PromoScreen()
    }
}

