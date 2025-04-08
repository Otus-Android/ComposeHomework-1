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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch
import ru.otus.marketsample.R
import ru.otus.marketsample.commonUi.DiscountText
import ru.otus.marketsample.commonUi.ErrorSnackbar
import ru.otus.marketsample.commonUi.LoadingProgressBar
import javax.inject.Inject


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
                Details(state.detailsState, modifier)
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
        ImageProduct(modifier = modifier, details.image)
        Name(details.name)
        Column(
            modifier = modifier.align(Alignment.End)
        ) {
            DiscountText(
                isVisible = details.hasDiscount,
                text = details.discount,
                modifier = modifier.align(Alignment.End)
            )
            PriseProduct(details.price, modifier = modifier.align(Alignment.End))
            AddToCartBtn()
        }
    }
}

@Composable
private fun ImageProduct(modifier: Modifier = Modifier, imageUrl: String) {
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
private fun Name(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = name,
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
private fun AddToCartBtn() {
    Button(
        modifier = Modifier.padding(horizontal = 10.dp),
        onClick = {}
    ) {
        Text(
            text = "Add to cart",
            fontSize = 18.sp,
        )
    }
}



