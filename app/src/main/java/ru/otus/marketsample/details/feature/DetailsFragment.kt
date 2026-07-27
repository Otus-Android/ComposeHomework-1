package ru.otus.marketsample.details.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.R
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var factory: DetailsViewModelFactory

    private val viewModel: DetailsViewModel by viewModels(
        factoryProducer = { factory }
    )

    private val productId by lazy { arguments?.getString("productId")!! }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerDetailsComponent.factory()
            .create(
                dependencies = findDependencies(),
                productId = productId,
            )
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DetailsScreen(viewModel)
            }
        }
    }
}

@Composable
private fun DetailsScreen(viewModel: DetailsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailsContent(
        state = state,
        onErrorShown = viewModel::errorHasShown,
        onAddToCartClick = { /* TODO: Добавить логику добавления в корзину */ }
    )
}

@Composable
private fun DetailsContent(
    state: DetailsScreenState,
    onErrorShown: () -> Unit,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        when {
            state.isLoading -> LoadingState(Modifier.align(Alignment.Center))
            state.hasError -> {
                ErrorState(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    errorMessage = state.errorProvider(context),
                    onErrorShown = onErrorShown
                )
            }

            else -> ProductState(
                detailsState = state.detailsState,
                onAddToCartClick = onAddToCartClick
            )
        }
    }
}

@Composable
fun LoadingState(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
fun ErrorState(
    modifier: Modifier,
    errorMessage: String,
    onErrorShown: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        scope.launch {
            snackbarHostState.showSnackbar(message = errorMessage)
        }
        onErrorShown()
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier
    )
}

@Composable
private fun ProductState(
    detailsState: DetailsState,
    onAddToCartClick: () -> Unit
) {
    val priceBrush = Brush.linearGradient(
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f),
        colors = listOf(Color(0xFF6200EE), Color(0xFFBB86FC))
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = detailsState.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = detailsState.name,
            color = Color(0xFF000000),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        if (detailsState.hasDiscount) {
            Text(
                text = detailsState.discount,
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(20.dp, 2.dp, 10.dp, 20.dp))
                    .background(priceBrush)
                    .padding(horizontal = 10.dp),
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp
            )
        }

        Text(
            text = stringResource(R.string.price_with_arg, detailsState.price),
            modifier = Modifier
                .align(Alignment.End)
                .padding(14.dp),
            color = Color(0xFF6200EE),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.size(10.dp))

        Button(
            onClick = onAddToCartClick,
            modifier = Modifier
                .align(Alignment.End)
                .padding(14.dp)
        ) {
            Text(
                text = stringResource(R.string.add_to_cart),
                fontSize = 18.sp
            )
        }
    }
}