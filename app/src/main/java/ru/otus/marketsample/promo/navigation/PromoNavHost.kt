package ru.otus.marketsample.promo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.otus.marketsample.di.ViewModelsFactory
import ru.otus.marketsample.navigation.Promo
import ru.otus.marketsample.promo.PromoScreen

fun NavGraphBuilder.promoGraph(
    navController: NavController,
    viewModelFactory: ViewModelsFactory
) {
    composable<Promo> {
        PromoScreen(
            viewModelsFactory = viewModelFactory
        )
    }
}