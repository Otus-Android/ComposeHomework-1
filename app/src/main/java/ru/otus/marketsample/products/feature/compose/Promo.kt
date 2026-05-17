package ru.otus.marketsample.products.feature.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.promo.feature.PromoScreenState
import ru.otus.marketsample.promo.feature.PromoState

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("NonSkippableComposable")
@Composable
fun PromoList(
    state: PromoScreenState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = onRefresh,
    ) {
        LazyColumn(modifier = modifier) {
            items(
                items = state.promoListState,
                key = { it.id }) { promo ->
                PromoItem(promoState = promo)
            }
        }
    }
}

@Composable
fun PromoItem(
    promoState: PromoState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.White)
            .padding(10.dp)
            .height(250.dp)
    ) {
        val isPreview =
            LocalInspectionMode.current

        if (isPreview) {//оставил для дебага, в рабочем коде бы убрал такое
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        } else {
            AsyncImage(
                model = promoState.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Column(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(10.dp)) {

            Text(
                text = promoState.name,
                fontSize = 24.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = promoState.description,
                fontSize = 14.sp,
                color = Color.White
                )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    PromoItem(
        PromoState(
            id = "11",
            name = "Здоровый старт",
            image = "https://otus-android.github.io/static/compose-hw1/img/product01.webp",
            description = "Blabla"
        )
    )
}