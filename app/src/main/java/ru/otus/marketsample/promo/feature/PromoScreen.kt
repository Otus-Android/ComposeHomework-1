package ru.otus.marketsample.promo.feature

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.otus.marketsample.di.AppComponent
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent

@Composable
fun PromoScreen(
    appComponent: AppComponent,
) {
    val component = remember {
        DaggerPromoComponent.factory().create(appComponent)
    }
    val context = LocalContext.current
    val factory = component.getFactory()
    val viewModel: PromoListViewModel = viewModel(factory = factory)
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
        else -> PromoContent(
            isLoading = state.isLoading,
            promoList = state.promoListState,
            onRefresh = viewModel::refresh,
        )
    }
}
