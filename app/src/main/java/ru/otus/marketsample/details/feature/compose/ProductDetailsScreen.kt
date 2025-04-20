package ru.otus.marketsample.details.feature.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.common.BadgeSize
import ru.otus.marketsample.common.DiscountBadge
import ru.otus.marketsample.common.ErrorHandler
import ru.otus.marketsample.common.Loader
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.common.ui.R as UiR

@Composable
fun ProductDetailsScreen(
    viewModel: DetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ErrorHandler(state.hasError) { viewModel.errorHasShown() }

    Loader(isLoading = state.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.End
        ) {
            AsyncImage(
                modifier = Modifier.height(300.dp),
                model = state.detailsState.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = state.detailsState.name,
                style = TextStyle.Default.copy(
                    fontSize = 24.sp,
                    color = Color.Black,
                ),
            )
            if (state.detailsState.hasDiscount) {
                DiscountBadge(
                    discount = state.detailsState.discount,
                    badgeSize = BadgeSize.L,
                )
            }
            Text(
                modifier = Modifier.padding(14.dp),
                text = stringResource(R.string.price_with_arg, state.detailsState.price),
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    color = colorResource(UiR.color.purple_500)
                )
            )
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { }
            ) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "ADD TO CART",
                    style = TextStyle.Default.copy(
                        fontSize = 20.sp,
                    )
                )
            }
        }
    }
}