package ru.otus.marketsample.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.otus.marketsample.navigate.Screen.Companion.KEY_PRODUCT_ID

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    promoContent: @Composable () -> Unit,
    productsContent: @Composable () -> Unit,
    detailsContent: @Composable (String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Products.route
    ) {
        composable(Screen.Products.route) {
            productsContent()
        }

        composable(Screen.Promo.route) {
            promoContent()
        }

        composable(Screen.Details.route) {
            val id = it.arguments?.getString(KEY_PRODUCT_ID) ?: error("Product ID is missing")
            detailsContent(id)
        }
    }
}