package ru.otus.marketsample.products.feature

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable;
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.glide.rememberGlidePainter
import ru.otus.marketsample.R
import ru.otus.marketsample.commonUi.DiscountText
import ru.otus.marketsample.commonUi.ErrorSnackbar
import ru.otus.marketsample.commonUi.LoadingProgressBar


@Preview(showSystemUi = true)
@Composable
fun ProductListScreenPreview() {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        when {
            state.isLoading -> {
                LoadingProgressBar(modifier = modifier)
            }

            state.hasError -> {
                ErrorSnackbar(
                    snackbarHostState = snackbarHostState,
                    message = "Error while loading data",
                    onErrorShown = { viewModel.errorHasShown() }
                )
            }

            else -> {
                PullToRefreshContent(
                    state = state,
                    onRefresh = { viewModel.refresh() },
                    navController = navController,
                    modifier = modifier
                )
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PullToRefreshContent(
    state: ProductsScreenState,
    onRefresh: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val statePullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { onRefresh() },
        modifier = modifier,
        state = statePullToRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = Color.White,
                color = Color.Black,
                state = statePullToRefreshState,
                isRefreshing = state.isRefreshing
            )
        }
    ) {
        List(state = state, navController = navController, modifier = modifier)
    }
}

@Composable
private fun List(
    state: ProductsScreenState,
    navController: NavController,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(state.productListState) {
            Product(productState = it, navController = navController, modifier = modifier)
        }
    }
}

@Composable
private fun Product(
    productState: ProductState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController
                    .navigate(
                        resId = R.id.action_main_to_details,
                        args = bundleOf("productId" to productState.id),
                    )
            }
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .height(130.dp)
        ) {
            ImageProduct(imageUrl = productState.image, modifier = modifier)
            DiscountText(
                text = productState.discount,
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                isVisible = productState.hasDiscount,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .height(130.dp)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Name(text = productState.name, modifier = modifier)
            Prise(prise = productState.price, modifier = modifier.align(Alignment.End))
        }
    }
}

@Composable
private fun ImageProduct(imageUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberGlidePainter(request = imageUrl),
        contentDescription = "",
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

@Composable
private fun Name(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        color = Color.Black,
        fontSize = 18.sp,
        maxLines = 2,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun Prise(prise: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .background(
                color = Color(0xfffff3e0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        text = stringResource(R.string.price_with_arg, prise),
        color = colorResource(id = ru.otus.common.ui.R.color.purple_500),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}