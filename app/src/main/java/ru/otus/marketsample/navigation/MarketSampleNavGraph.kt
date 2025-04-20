package ru.otus.marketsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.details.feature.compose.ProductDetailsScreen
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.utils.daggerViewModel


const val PRODUCT_ID_ARGUMENT = "productId"

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
        MainScreen(
            navController = navController,
        )
    }

    composable(
        route = MarketSampleScreens.PRODUCT_DETAILS.name +
        "/{$PRODUCT_ID_ARGUMENT}",
    ) { entry ->
        val appComponent = (LocalContext.current.applicationContext as MarketSampleApp)
            .appComponent
        val viewModel = daggerViewModel {
            DaggerDetailsComponent.factory().create(
                dependencies = appComponent,
                productId = entry.arguments?.getString(PRODUCT_ID_ARGUMENT).orEmpty(),
            ).viewModel()
        }
        ProductDetailsScreen(
            viewModel = viewModel,
        )
    }
}