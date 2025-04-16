package ru.otus.marketsample.promo.feature.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.promo.feature.PromoScreenState
import ru.otus.marketsample.promo.feature.PromoState
import theme.MarketSampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoScreen(
    state: PromoScreenState,
    errorHasShown: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Box(modifier.fillMaxSize()) {
        when {
            state.hasError -> {
                Toast.makeText(context, state.errorProvider(context), Toast.LENGTH_SHORT).show()
                errorHasShown()
            }

            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                ) {
                    LazyColumn {
                        items(state.promoListState, { it.id }) { promoState ->
                           PromoListItem(promoState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PromoListItem(
    promoState: PromoState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        AsyncImage(
            model = promoState.image,
            contentDescription = null,
            modifier = Modifier.height(250.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .drawBehind {
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0x00000000),
                                Color(0xaa000000),
                            ),
                            start = Offset(size.width / 2, 0f),
                            end = Offset(size.width / 2, size.height)
                        ),
                    )
                }
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(
                text = promoState.name,
                color = Color.White,
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = promoState.description,
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun PromoScreenPreview() {
    MarketSampleTheme {
        PromoScreen(
            state = PromoScreenState(
                isLoading = false,
                promoListState = listOf(
                    PromoState(
                        id = "1",
                        name = "Name",
                        description = "Bla bla bla",
                        image = "image"
                    )
                ),
                hasError = false,
            ),
            errorHasShown = { },
            isRefreshing = false,
            onRefresh = { },
        )
    }
}