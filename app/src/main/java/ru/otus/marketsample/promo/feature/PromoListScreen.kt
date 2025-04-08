package ru.otus.marketsample.promo.feature

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.glide.rememberGlidePainter
import ru.otus.marketsample.commonUi.ErrorSnackbar
import ru.otus.marketsample.commonUi.LoadingProgressBar


@Preview(showSystemUi = true)
@Composable
fun PromoListScreenPreview() {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PromoListScreen(
    viewModel: PromoListViewModel,
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
                PullToRefreshContent(
                    state,
                    { viewModel.refresh() },
                    modifier
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PullToRefreshContent(
    state: PromoScreenState,
    onRefresh: () -> Unit,
    modifier: Modifier,
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
        List(modifier, state)
    }
}

@Composable
private fun List(
    modifier: Modifier,
    state: PromoScreenState
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(state.promoListState) {
            Promo(it, modifier)
        }
    }
}


@Composable
private fun Promo(promoState: PromoState, modifier: Modifier = Modifier) {
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
            Name(promoState.name)
            Description(promoState.description)
        }
    }
}

@Composable
private fun PromoImage(modifier: Modifier = Modifier, imageUrl: String) {
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
private fun Name(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = name,
        color = Color.White,
        fontSize = 24.sp,
    )

}

@Composable
private fun Description(description: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = description,
        color = Color.White,
        fontSize = 14.sp,
    )
}