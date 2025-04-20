package ru.otus.marketsample.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.products.feature.compose.ProductListScreen
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.promo.feature.compose.PromoListScreen
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import ru.otus.marketsample.utils.daggerViewModel

@Composable
fun BottomNavigationNavGraph(
    hostNavController: NavHostController,
    bottomNavigationNavController: NavHostController,
    paddingValues: PaddingValues,
) = NavHost(
    navController = bottomNavigationNavController,
    modifier = Modifier.padding(paddingValues).background(color = Color.White),
    startDestination = MarketSampleScreens.PRODUCTS_LIST.name,
) {
    composable(
        route = MarketSampleScreens.PRODUCTS_LIST.name,
    ) {
        val appComponent = (LocalContext.current.applicationContext as MarketSampleApp)
            .appComponent
        val viewModel = daggerViewModel {
            DaggerProductListComponent.factory().create(
                dependencies = appComponent,
            ).viewModel()
        }
        ProductListScreen(
            viewModel = viewModel,
            navController = hostNavController,
        )
    }

    composable(
        route = MarketSampleScreens.PROMO_LIST.name,
    ) {
        val appComponent = (LocalContext.current.applicationContext as MarketSampleApp)
            .appComponent
        val viewModel = daggerViewModel {
            DaggerPromoComponent.factory().create(
                dependencies = appComponent,
            ).viewModel()
        }
        PromoListScreen(
            viewModel = viewModel,
        )
    }
}

