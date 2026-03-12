package ru.otus.marketsample.products.feature

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.otus.marketsample.common.Screen
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent

@Composable
fun ProductsScreen(
    appComponent: AppComponent,
    navController: NavController
) {
    val component = remember {
        DaggerProductListComponent.factory().create(appComponent)
    }
    val context = LocalContext.current
    val factory = component.getFactory()
    val viewModel: ProductListViewModel = viewModel(factory = factory)
    val state by viewModel.state.collectAsState()
    when {
        state.hasError -> {
            Toast.makeText(
                context,
                "Error wile loading data",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.errorHasShown()
        }

        else -> ProductsContent(
            isLoading = state.isLoading,
            products = state.productListState,
            onRefresh = viewModel::refresh,
            onItemClicked = { productId ->
                navController.navigate(Screen.Details.createRoute(productId))
            },
        )
    }
}
