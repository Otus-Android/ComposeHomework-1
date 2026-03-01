package ru.otus.marketsample.products.feature

//import coil.request.ImageRequest
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import ru.otus.common.ui.R
import ru.otus.components.Badge
import ru.otus.components.Price
import ru.otus.components.ProductCard
import ru.otus.marketsample.di.ViewModelsFactory

@Composable
fun ProductsScreen(
    viewModelFactory: ViewModelsFactory,
    viewModel: ProductListViewModel = viewModel(factory = viewModelFactory),
    showSnackbarCallback: (Boolean) -> Unit,
    openDetail: (id: String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Surface(
        color = colorResource(R.color.white),
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.hasError -> {
                showSnackbarCallback.invoke(state.hasError)

                viewModel.errorHasShown()
            }

            else -> Content(state, openDetail)
        }
    }
}

@Composable
private fun Content(state: ProductsScreenState, openDetail: (id: String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        items(
            count = state.productListState.size,
            key = { index -> state.productListState[index].id },
            contentType = { index -> state.productListState[index] }
        ) { index ->
            val item = state.productListState[index]
            ProductCard(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .clickable {
                        openDetail.invoke(item.id)
                    },
                image = {
                    AsyncImage(
                        model = item.image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                },
                badge = {
                    if (item.hasDiscount) {
                        Badge(
                            value = item.discount,
                            modifier = Modifier
                                .align(Alignment.TopEnd),
                        )
                    }
                },
                title = {
                    Text(
                        text = item.name,
                        modifier = Modifier
                            .fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 18.sp,
                        color = colorResource(R.color.black),
                        fontWeight = FontWeight.Medium
                    )
                },
                price = {
                    Price(
                        value = item.price,
                        modifier = Modifier
                            .align(Alignment.End)
                    )
                }
            )
        }
    }
}