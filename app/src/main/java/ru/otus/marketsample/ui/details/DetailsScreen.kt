package ru.otus.marketsample.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.R
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.theme.ImageBackground
import ru.otus.marketsample.theme.Purple200
import ru.otus.marketsample.theme.Purple500
import ru.otus.marketsample.theme.White
import ru.otus.marketsample.util.LocalSpacing

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: String,
    onClick: () -> Unit
) {

    val detailComponent = (LocalContext.current.applicationContext as MarketSampleApp)
        .appComponent
        .getViewModelComponentFactory()
        .create(id)

    val viewModel: DetailsViewModel = viewModel(factory = detailComponent.getViewModelFactory())

    val state by viewModel.state.collectAsState()

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, productName, discount, price, button, progressBar) = createRefs()
        val spacing = LocalSpacing.current
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = ImageBackground)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            model = state.detailsState.image,
            contentDescription = state.detailsState.name,
            contentScale = ContentScale.Crop,
            clipToBounds = true
        )
        Text(
            modifier = Modifier
                .constrainAs(productName) {
                    start.linkTo(parent.start)
                    top.linkTo(image.bottom)
                }
                .wrapContentWidth(),
            text = state.detailsState.name,
            fontSize = 24.sp,
            color = Color.Black,
        )
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
        if (state.detailsState.hasDiscount) {
            Text(
                modifier = Modifier
                    .constrainAs(discount) {
                        end.linkTo(parent.end)
                        top.linkTo(productName.bottom)
                    }
                    .padding(horizontal = 10.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Purple500, Purple200),
                            start = Offset(0f, 0f),
                            end = Offset(50f, 50f)
                        ),
                        shape = RoundedCornerShape(
                            topStart = 40.dp,
                            topEnd = 10.dp,
                            bottomEnd = 40.dp,
                            bottomStart = 40.dp
                        )
                    )
                    .border(
                        2.dp, White, shape = RoundedCornerShape(
                            topStart = 40.dp,
                            topEnd = 10.dp,
                            bottomEnd = 40.dp,
                            bottomStart = 40.dp
                        )
                    )
                    .padding(horizontal = 10.dp, vertical = spacing.extraSmall),
                text = state.detailsState.discount,
                fontSize = 20.sp,
                color = White
            )
        }
        Text(
            modifier = Modifier
                .constrainAs(price) {
                    end.linkTo(parent.end)
                    if (state.detailsState.hasDiscount) {
                        top.linkTo(discount.bottom)
                    } else {
                        top.linkTo(productName.bottom)
                    }
                }
                .padding(14.dp),
            text = stringResource(R.string.price_with_arg, state.detailsState.price),
            fontSize = 16.sp,
            color = Purple500,
            fontWeight = Bold
        )
        Box(
            modifier = Modifier
                .padding(10.dp)
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(price.bottom)
                },
        ) {
            Button(
                contentPadding = PaddingValues(),
                shape = MaterialTheme.shapes.small,
                onClick = { onClick() }
            ) {
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "Add to cart",
                    fontSize = 18.sp,
                )
            }
        }
    }
}