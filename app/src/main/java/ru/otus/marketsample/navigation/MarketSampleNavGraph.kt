package ru.otus.marketsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.otus.marketsample.getApplicationComponent
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.compose.ProductListScreen
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent

@Composable
fun MarketSampleNavGraph(
    navigationState: NavigationState,
) {
    NavHost(navController = navigationState.navHostController, startDestination = Route.Products) {
        navigation<Route.Products>(startDestination = ProductsRoute.List) {
            composable<ProductsRoute.List> {
                val component = getApplicationComponent()
                val productListComponent = remember {
                    DaggerProductListComponent.factory()
                        .create(component)
                }
                val viewModel = viewModel(ProductListViewModel::class, viewModelStoreOwner = it, factory = productListComponent.getProductListViewModelFactory())

                val state by viewModel.state.collectAsState()

                ProductListScreen(
                    state = state,
                    errorHasShown = { viewModel.errorHasShown() }
                )
            }
            composable<ProductsRoute.Details> {  }
        }

        composable<Route.Promo> {

        }
    }
}