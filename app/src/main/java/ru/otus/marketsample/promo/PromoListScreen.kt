package ru.otus.marketsample.promo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromoState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreen(
    viewModel: PromoListViewModel
) {
    val viewState by viewModel.state.collectAsState()
    val pullState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = viewState.isLoading,
        onRefresh = { viewModel.refresh() },
        state = pullState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
            ,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            viewState.promoListState.forEach {
                PromoItem(item = it)
            }
        }
    }
}

@Composable
private fun PromoItem(
    item: PromoState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.image,
            contentDescription = "Изображение товара",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(100.dp)
                .padding(10.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.name,
                fontSize = 24.sp,
                color = Color.White
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = item.description,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}