package ru.otus.marketsample.products.feature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.ProductDetailsComposeScreen
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent

@Composable
internal fun ProductScreenNavHost(productListViewModel: ProductListViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val state = productListViewModel.state.collectAsState().value
            ProductListComposeScreen(
                state = state,
                onRefresh = { productListViewModel.refresh() },
                onItemClick = { productId ->
                    navController.navigate("details/$productId")
                }
            )
        }

        composable(
            route = "details/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val context = LocalContext.current
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
            val appComponent = (context.applicationContext as MarketSampleApp).appComponent
            val detailsComponent = DaggerDetailsComponent.factory()
                .create(appComponent, productId)
            val factory = detailsComponent.detailsViewModelFactory()
            val detailsViewModel = viewModel<DetailsViewModel>(factory = factory)
            val state = detailsViewModel.state.collectAsState().value
            ProductDetailsComposeScreen(state = state)
        }
    }
}