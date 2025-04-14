package ru.otus.marketsample.details.feature

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.glide.rememberGlidePainter
import ru.otus.marketsample.R
import ru.otus.marketsample.commonUi.DiscountText
import ru.otus.marketsample.commonUi.ErrorSnackbar
import ru.otus.marketsample.commonUi.LoadingProgressBar


@Preview(showSystemUi = true)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun DetailsScreenPreview() {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(viewModel: DetailsViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        when {
            state.isLoading -> {
                LoadingProgressBar()
            }

            state.hasError -> {
                ErrorSnackbar(
                    snackbarHostState = snackbarHostState,
                    message = "Error while loading data",
                    onErrorShown = { viewModel.errorHasShown() }
                )
            }

            else -> {
                Details(details = state.detailsState)
            }
        }
    }
}

@Composable
private fun Details(details: DetailsState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        ImageProduct(imageUrl = details.image)
        Name(text = details.name)
        Column(
            modifier = modifier.align(Alignment.End)
        ) {
            DiscountText(
                text = details.discount,
                modifier = modifier.align(Alignment.End),
                isVisible = details.hasDiscount
            )
            PriseProduct(prise = details.price, modifier = modifier.align(Alignment.End))
            AddToCartBtn()
        }
    }
}

@Composable
private fun ImageProduct(imageUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberGlidePainter(request = imageUrl),
        contentDescription = "",
        modifier = modifier
            .fillMaxWidth()
            .size(300.dp),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopStart
    )
}

@Composable
private fun Name(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.Black,
        fontSize = 24.sp
    )
}

@Composable
private fun PriseProduct(prise: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(14.dp),
        text = stringResource(R.string.price_with_arg, prise),
        fontSize = 18.sp,
        color = colorResource(id = ru.otus.common.ui.R.color.purple_500)
    )
}

@Composable
private fun AddToCartBtn(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(horizontal = 10.dp),
        onClick = {}
    ) {
        Text(
            text = "Add to cart",
            fontSize = 18.sp,
        )
    }
}



