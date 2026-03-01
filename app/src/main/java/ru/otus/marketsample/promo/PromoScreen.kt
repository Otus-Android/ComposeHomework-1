package ru.otus.marketsample.promo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import ru.otus.components.PromoCard
import ru.otus.marketsample.di.ViewModelsFactory
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.common.ui.R as RUi

@Composable
fun PromoScreen(
    viewModelsFactory: ViewModelsFactory,
    viewModel: PromoListViewModel = viewModel(factory = viewModelsFactory)
) {
    val state by viewModel.state.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(RUi.color.white)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(
                count = state.promoListState.size,
                key = { state.promoListState[it].id },
                contentType = { state.promoListState[it] },
            ) { index ->
                val promo = state.promoListState[index]
                PromoCard(
                    image = {
                        AsyncImage(
                            model = promo.image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    },
                    title = {
                        Text(
                            text = promo.name,
                            fontSize = 24.sp,
                            color = colorResource(RUi.color.white),
                        )
                    },
                    description = {
                        Text(
                            text = promo.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colorResource(RUi.color.white)
                        )
                    }
                )
            }
        }
    }
}