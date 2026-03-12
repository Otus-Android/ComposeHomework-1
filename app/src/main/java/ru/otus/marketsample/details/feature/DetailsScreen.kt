package ru.otus.marketsample.details.feature

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.otus.marketsample.common.Loader
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.di.AppComponent

@Composable
fun DetailsScreen(
    appComponent: AppComponent,
    productId: String,
) {
    val component = remember {
        DaggerDetailsComponent.factory()
            .create(
                dependencies = appComponent,
                productId = productId,
            )
    }
    val context = LocalContext.current
    val factory = component.getFactory()
    val viewModel: DetailsViewModel = viewModel(factory = factory)

    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> Loader()
        state.hasError -> {
            Toast.makeText(
                context,
                "Error wile loading data",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.errorHasShown()
        }
        else -> DetailsContent(
            state.detailsState,
        )
    }
}