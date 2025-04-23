package ru.otus.marketsample.promo.feature.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.otus.common.composeui.PaletteColors
import ru.otus.marketsample.R
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromoScreenState
import ru.otus.marketsample.promo.feature.PromoState

@Composable
fun PromoListScreen(
    viewModel: PromoListViewModel,
) {
    val state: PromoScreenState by viewModel.state.collectAsStateWithLifecycle()
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

            else -> if (state.promoListState.isEmpty()) {
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
                PromoListReady(
                    promos = state.promoListState,
                    doRefresh = { viewModel.refresh() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PromoListReady(promos: List<PromoState>, doRefresh: () -> Unit) {
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
                items(promos, key = { it.id }) {
                    PromoItem(
                        promo = it,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            }
        }

        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun PromoItem(promo: PromoState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        AsyncImage(
            model = promo.image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Transparent, PaletteColors.Black67)
                    )
                ),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = promo.name,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 24.sp
            )
            Text(
                text = promo.description,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 13.sp,
                lineHeight = 14.sp,
            )
        }
    }
}