package ru.otus.marketsample.products.feature.navigation

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.otus.marketsample.R
import ru.otus.marketsample.di.ViewModelsFactory
import ru.otus.marketsample.navigation.Products
import ru.otus.marketsample.products.feature.ProductsScreen

fun NavGraphBuilder.productGraph(
    navController: NavController,
    viewModelFactory: ViewModelsFactory,
    showSnackbarCallback: (Boolean) -> Unit,
    openDetail: (id: String) -> Unit,
) {
    composable<Products> {

        ProductsScreen(
            viewModelFactory = viewModelFactory,
            showSnackbarCallback = showSnackbarCallback,
            openDetail = openDetail
        )
    }
}
