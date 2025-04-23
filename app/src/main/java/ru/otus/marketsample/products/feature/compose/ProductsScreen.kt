package ru.otus.marketsample.products.feature.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.otus.common.composeui.PaletteColors
import ru.otus.marketsample.R
import ru.otus.marketsample.compose.theme.AppTheme
import ru.otus.marketsample.compose.theme.Inject
import ru.otus.marketsample.compose.theme.PreviewHandler
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductState
import ru.otus.marketsample.products.feature.ProductsScreenState

@Composable
fun ProductsScreen(
    viewModel: ProductListViewModel,
    onItemClicked: (String) -> Unit,
) {
    val state: ProductsScreenState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.hasError -> {
                Toast.makeText(
                    context,
                    state.errorProvider(context),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.errorHasShown()
            }

            state.isLoading -> {
                CircularProgressIndicator()
            }

            else -> if (state.productListState.isEmpty()) {
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { viewModel.refresh() }) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.button_try_again)
                    )
                }
            } else {
                ProductsReady(
                    products = state.productListState,
                    doRefresh = { viewModel.refresh() },
                    onItemClicked = onItemClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProductsReady(
    products: List<ProductState>,
    doRefresh: () -> Unit,
    onItemClicked: (String) -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() =
        refreshScope.launch {
            refreshing = true
            doRefresh()
            refreshing = false
        }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(state)) {
        LazyColumn {
            if (!refreshing) {
                items(products, key = { it.id }) {
                    ProductItem(it, modifier = Modifier.clickable {
                        onItemClicked(it.id)
                    })
                }
            }
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun ProductItem(
    product: ProductState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AsyncImage(
                model = product.image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .height(130.dp)
            )
            if (product.hasDiscount) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = product.discount,
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    topStart = 40.dp,
                                    topEnd = 5.dp,
                                    bottomEnd = 15.dp,
                                    bottomStart = 40.dp
                                )
                            )
                            .background(MaterialTheme.colors.onPrimary)
                            .padding(2.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 40.dp,
                                    topEnd = 1.dp,
                                    bottomEnd = 13.dp,
                                    bottomStart = 40.dp
                                )
                            )
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        PaletteColors.Purple200,
                                        PaletteColors.Purple500
                                    ),
                                    start = Offset(0f, Float.POSITIVE_INFINITY),
                                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                                )
                            )
                            .padding(start = 7.dp, end = 7.dp, top = 0.dp, bottom = 0.dp),
                        fontSize = 14.sp,
                        maxLines = 1,
                        color = PaletteColors.White,
                        fontWeight = FontWeight.Bold,
                        style = LocalTextStyle.current.copy(
                            platformStyle = PlatformTextStyle(includeFontPadding = false)
                        ),
                        lineHeight = 24.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = product.name,
                color = MaterialTheme.colors.onSecondary,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = stringResource(R.string.price_with_arg, product.price),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PaletteColors.CreamyCloudDreams)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                color = PaletteColors.Purple500,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun PreviewProductItem() {
    AppTheme {
        PreviewHandler().Inject {
            Box(modifier = Modifier.fillMaxSize()) {
                val product = ProductState(
                    id = "10",
                    name = "Product Name",
                    image = "https://image1.png",
                    price = "2000,00",
                    hasDiscount = true,
                    discount = "10 %"
                )
                ProductItem(product)
            }
        }
    }
}

