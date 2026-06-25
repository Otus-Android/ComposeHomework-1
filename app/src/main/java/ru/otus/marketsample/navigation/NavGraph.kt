package ru.otus.marketsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.otus.marketsample.details.feature.DetailsScreen
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.products.feature.ProductListScreen
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.promo.feature.PromoListScreen
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent

@Composable
fun CreateGraph(
    navController: NavHostController,
    appComponent: AppComponent,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "products"
    ) {
        composable("products") {
            val productListComponent = remember {
                DaggerProductListComponent.factory().create(appComponent)
            }

            val productListViewModelFactory = remember {
                productListComponent.getViewModelFactory()
            }

            val productListViewModel: ProductListViewModel =
                viewModel(factory = productListViewModelFactory)

            ProductListScreen(
                viewModel = productListViewModel,
                onClick = { productId ->
                    navController.navigate("details/$productId")
                }
            )
        }

        composable("promo") {
            val promoListComponent = remember {
                DaggerPromoComponent.factory().create(appComponent)
            }

            val promoListViewModelFactory = remember {
                promoListComponent.getViewModelFactory()
            }

            val promoListViewModel: PromoListViewModel =
                viewModel(factory = promoListViewModelFactory)

            PromoListScreen(
                viewModel = promoListViewModel
            )
        }

        composable("details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""

            val detailsComponent = remember {
                DaggerDetailsComponent.factory().create(
                    dependencies = appComponent,
                    productId = productId
                )
            }

            val detailsViewModelFactory = remember {
                detailsComponent.getViewModelFactory()
            }

            val detailsViewModel: DetailsViewModel =
                viewModel(factory = detailsViewModelFactory)

            DetailsScreen(
                viewModel = detailsViewModel
            )
        }
    }
}