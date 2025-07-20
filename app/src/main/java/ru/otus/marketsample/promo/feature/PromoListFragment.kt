package ru.otus.marketsample.promo.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.details.feature.ErrorState
import ru.otus.marketsample.details.feature.LoadingState
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import javax.inject.Inject

class PromoListFragment : Fragment() {

    @Inject
    lateinit var factory: PromoListViewModelFactory

    private val viewModel: PromoListViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerPromoComponent.factory()
            .create(dependencies = findDependencies())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                PromoListScreen(viewModel)
            }
        }
    }
}

@Composable
private fun PromoListScreen(viewModel: PromoListViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isRefreshing by remember { mutableStateOf(false) }

    PromoListContent(
        state = state,
        onErrorShown = viewModel::errorHasShown,
        isRefreshing = isRefreshing,
        refresh = viewModel::refresh
    )
}

@Composable
private fun PromoListContent(
    state: PromoScreenState,
    onErrorShown: () -> Unit,
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

            else -> PromoListState(
                promos = state.promoListState,
                isRefreshing = isRefreshing,
                onRefresh = refresh
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PromoListState(
    promos: List<PromoState>,
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
            modifier = Modifier.fillMaxSize()
        ) {
            items(promos) { promo ->
                PromoItem(promo)
            }
        }
    }
}

@Composable
private fun PromoItem(promo: PromoState) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0x00000000), Color(0xaa000000))
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        AsyncImage(
            model = promo.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(100.dp)
                .background(gradient)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = promo.name,
                color = Color(0xFFFFFFFF),
                fontSize = 24.sp
            )
            Text(
                text = promo.description,
                color = Color(0xFFFFFFFF),
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPromoItem() {
    PromoItem(
        PromoState(
            id = "",
            name = "Name",
            description = "Bla bla bla bla",
            image = ""
        )
    )
}
