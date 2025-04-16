package ru.otus.marketsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.compose.DetailsScreen
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.getApplicationComponent
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.compose.ProductListScreen
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.compose.PromoScreen
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent

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
                val viewModel = viewModel(
                    ProductListViewModel::class,
                    viewModelStoreOwner = it,
                    factory = productListComponent.getProductListViewModelFactory()
                )

                val state by viewModel.state.collectAsState()

                ProductListScreen(
                    state = state,
                    errorHasShown = { viewModel.errorHasShown() },
                    isRefreshing = state.isRefreshing,
                    onRefresh = viewModel::refresh
                )
            }

            composable<ProductsRoute.Details> {
                val component = getApplicationComponent()
                val productListComponent = remember {
                    DaggerDetailsComponent.factory()
                        .create(component, it.id)
                }

                val viewModel = viewModel(
                    DetailsViewModel::class,
                    viewModelStoreOwner = it,
                    factory = productListComponent.getDetailsViewModelFactory()
                )

                val state by viewModel.state.collectAsState()

                DetailsScreen(
                    state = state,
                    errorHasShown = viewModel::errorHasShown
                )
            }
        }

        composable<Route.Promo> {
            val component = getApplicationComponent()
            val promoListComponent = remember {
                DaggerPromoComponent.factory()
                    .create(component)
            }

            val viewModel = viewModel(
                PromoListViewModel::class,
                viewModelStoreOwner = it,
                factory = promoListComponent.getPromoViewModelFactory()
            )

            val state by viewModel.state.collectAsState()

            PromoScreen(
                state = state,
                errorHasShown = viewModel::errorHasShown,
                isRefreshing = state.isRefreshing,
                onRefresh = viewModel::refresh,
            )
        }
    }
}