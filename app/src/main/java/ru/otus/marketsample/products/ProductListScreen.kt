package ru.otus.marketsample.products


import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel
) {
    val viewState by viewModel.state.collectAsState()
    val pullState = rememberPullToRefreshState()
    var productState: ProductState? by remember {
        mutableStateOf(null)
    }
    SharedTransitionLayout {
        AnimatedContent(
            productState,
            label = "basic_transition"
        ) { state ->
            if (state != null) {
                ProductItemDetail(
                    animatedVisibilityScope = this@AnimatedContent,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    item = state,
                    onBack = {
                        productState = null
                    }
                )
            } else {
                PullToRefreshBox(
                    isRefreshing = viewState.isLoading,
                    onRefresh = { viewModel.refresh() },
                    state = pullState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        items(
                            items = viewState.productListState,
                            key = { it.id }
                        ) {
                            ProductItemShort(
                                animatedVisibilityScope = this@AnimatedContent,
                                sharedTransitionScope = this@SharedTransitionLayout,
                                item = it,
                                onItemClick = { pr ->
                                    productState = pr
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductItemShort(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    item: ProductState,
    onItemClick: (ProductState) -> Unit
) {
    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = item.id),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .fillMaxWidth()
                .height(130.dp)
                .clickable { onItemClick(item) }
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                    model = item.image,
                    contentDescription = "Изображение товара",
                    contentScale = ContentScale.Crop
                )
                if (item.hasDiscount) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp,
                                )
                            )
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF64B5F6),
                                        Color.Blue
                                    )// от темно-синего к светло-синему
                                    // start = Offset(0f, 0f), end = Offset(0f, Float.POSITIVE_INFINITY) // вертикальный градиент (сверху вниз)
                                    // по умолчанию градиент идёт слева направо (горизонтальный)
                                )
                            )
                            .border(
                                2.dp,
                                Color.White,
                                RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp,
                                )
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 4.dp
                            ),
                        text = item.discount,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    text = item.name,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    maxLines = 2,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEDE8D0))
                        .padding(8.dp)
                        .align(Alignment.TopStart),
                    text = item.price + "руб",
                    fontSize = 18.sp,
                    color = Color(0xFF6200EE),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ProductItemDetail(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    item: ProductState,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .sharedElement(
                    rememberSharedContentState(key = item.id),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                model = item.image,
                contentDescription = "Изображение товара",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.name,
                fontSize = 24.sp,
                textAlign = TextAlign.Start
            )
            if (item.hasDiscount) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 0.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp,
                            )
                        )
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF64B5F6),
                                    Color.Blue
                                )
                            )
                        )
                        .border(
                            2.dp,
                            Color.White,
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 0.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp,
                            )
                        )
                        .padding(
                            horizontal = 10.dp,
                            vertical = 4.dp
                        ),
                    text = item.discount,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier.padding(10.dp),
                text = item.price + "руб",
                fontSize = 18.sp,
                color = Color(0xFF6200EE)
            )
            Button(
                shape = RoundedCornerShape(6.dp),
                colors = ButtonColors(
                    containerColor = Color.Blue, contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                modifier = Modifier.padding(10.dp),
                onClick = {}
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "ADD TO CART",
                    fontSize = 18.sp
                )
            }
        }
    }
}