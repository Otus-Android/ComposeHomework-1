package ru.otus.marketsample.products.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import coil.compose.AsyncImage
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.R
import ru.otus.marketsample.details.feature.ErrorState
import ru.otus.marketsample.details.feature.LoadingState
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import javax.inject.Inject

class ProductListFragment : Fragment() {

    @Inject
    lateinit var factory: ProductListViewModelFactory

    private val viewModel: ProductListViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (activity?.applicationContext as MarketSampleApp).appComponent

        DaggerProductListComponent.factory()
            .create(appComponent)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ProductListScreen(
                    navigateToDetails = { productId ->
                        requireActivity().findNavController(R.id.nav_host_activity_main)
                            .navigate(
                                resId = R.id.action_main_to_details,
                                args = bundleOf("productId" to productId),
                            )
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
private fun ProductListScreen(
    navigateToDetails: (productId: String) -> Unit,
    viewModel: ProductListViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isRefreshing by remember { mutableStateOf(false) }

    ProductListContent(
        state = state,
        onErrorShown = viewModel::errorHasShown,
        navigateToDetails = navigateToDetails,
        isRefreshing = isRefreshing,
        refresh = viewModel::refresh
    )
}

@Composable
private fun ProductListContent(
    state: ProductsScreenState,
    onErrorShown: () -> Unit,
    navigateToDetails: (productId: String) -> Unit,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        when {
            state.isLoading -> LoadingState(Modifier.align(Alignment.Center))
            state.hasError -> {
                ErrorState(modifier = Modifier.align(Alignment.BottomCenter), onErrorShown)
            }

            else -> ProductListState(
                products = state.productListState,
                onItemClick = { productId ->
                    navigateToDetails(productId)
                },
                isRefreshing = isRefreshing,
                onRefresh = refresh
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductListState(
    products: List<ProductState>,
    onItemClick: (String) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onClick = { onItemClick(product.id) }
                )
            }
        }
    }
}

@Composable
private fun ProductItem(
    product: ProductState,
    onClick: () -> Unit
) {
    val priceBrush = Brush.linearGradient(
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f),
        colors = listOf(Color(0xFF6200EE), Color(0xFFBB86FC))
    )

    Row(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .height(130.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            if (product.hasDiscount) {
                Text(
                    text = product.discount,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .border(
                            2.dp,
                            Color(0xFFFFFFFF),
                            RoundedCornerShape(20.dp, 2.dp, 10.dp, 20.dp)
                        )
                        .clip(RoundedCornerShape(20.dp, 2.dp, 10.dp, 20.dp))
                        .background(priceBrush)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(
                text = product.name,
                modifier = Modifier.align(Alignment.TopStart),
                color = Color(0xFF000000),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = stringResource(R.string.price_with_arg, product.price),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 12.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFFFF3E0),
                            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                        )
                    }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                color = Color(0xFF6200EE),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProductItem() {
    ProductItem(
        ProductState(
            id = "2",
            name = "Product Name",
            image = "",
            price = "200",
            hasDiscount = true,
            discount = "-20%"
        )
    ) { }
}