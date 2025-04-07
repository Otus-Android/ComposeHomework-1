package ru.otus.marketsample.promo.feature

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.glide.rememberGlidePainter



@Preview(showSystemUi = true)
@Composable
fun PromoListScreenPreview() {
 /*   ItemPromo(PromoState("", "Здоровый спорт", "teeeeeeeeeeeeeeeeeeeeeec", "r"))*/
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoListScreen(
    viewModel: PromoListViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    val statePullToRefreshState = rememberPullToRefreshState()

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else if (state.hasError) {
        Toast.makeText(LocalContext.current, "Error while loading data", Toast.LENGTH_SHORT).show()
        viewModel.errorHasShown()
    } else {
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.refresh() },
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
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(state.promoListState) {
                    ItemPromo(it, modifier)
                }
            }
        }
    }

}

@Composable
fun ItemPromo(promoState: PromoState, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        PromoImage(modifier, promoState.image)
        Column(
            modifier = modifier
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x00000000),
                            Color(0xAA000000),
                        )
                    )
                )
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            NamePromo(promoState.name)
            DescriptionPromo(promoState.description)
        }
    }
}

@Composable
fun PromoImage(modifier: Modifier = Modifier, imageUrl: String) {
    Image(
        painter = rememberGlidePainter(request = imageUrl),
        contentDescription = "",
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

@Composable
fun NamePromo(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = name,
        color = Color.White,
        fontSize = 24.sp,
    )

}

@Composable
fun DescriptionPromo(description: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = description,
        color = Color.White,
        fontSize = 14.sp,
    )
}