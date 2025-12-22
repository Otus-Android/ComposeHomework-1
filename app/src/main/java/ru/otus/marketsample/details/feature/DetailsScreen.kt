package ru.otus.marketsample.details.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.otus.marketsample.common.Discount
import ru.otus.marketsample.navigation.SnackbarController
import ru.otus.marketsample.navigation.SnackbarEvent
import theme.MarketSampleTheme
import theme.black
import theme.purple_500
import theme.white

@Composable
fun DetailsScreen(
    state: DetailsScreenState,
    errorHasShown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = white)
            },
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.hasError -> {
                LaunchedEffect(Unit) {
                    scope.launch {
                        SnackbarController.sendEvent(
                            SnackbarEvent(state.errorProvider(context))
                        )
                    }
                }
                errorHasShown()
            }

            else -> {
                Content(state)
            }
        }
    }
}

@Composable
private fun Content(
    state: DetailsScreenState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AsyncImage(
            model = state.detailsState.image,
            contentDescription = null,
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = state.detailsState.name,
            color = black,
            fontSize = 24.sp,
        )

        if (state.detailsState.hasDiscount) {
            Discount(
                discount = state.detailsState.discount,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.align(Alignment.End),
            )
        }

        Text(
            text = state.detailsState.price,
            color = purple_500,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(14.dp)
                .align(Alignment.End),
        )
        Button(
            onClick = { },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .padding(14.dp)
                .align(Alignment.End),
        ) {
            Text(
                text = "ADD TO CART",
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun DetailsScreenPreview() {
    MarketSampleTheme {
        DetailsScreen(
            state = DetailsScreenState(
                isLoading = false,
                detailsState = DetailsState(
                    name = "Product Name",
                    price = "2000 руб",
                    hasDiscount = true,
                    discount = "-20%",
                ),
                hasError = false,
            ),
            errorHasShown = { },
        )
    }
}
