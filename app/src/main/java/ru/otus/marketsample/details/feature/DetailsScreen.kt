package ru.otus.marketsample.details.feature

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import ru.otus.marketsample.R

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource


@Preview(showSystemUi = true)
@Composable
fun DetailsScreenPreview() {

    DetailsScreen(null)

}

@Composable
fun DetailsScreen(viewModel: DetailsViewModel?, modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(DetailsScreenState()) }
    LaunchedEffect(Unit) {
        viewModel!!.state.collect { newState ->
            state = newState
        }
    }
    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    } else if (state.hasError) {
        Toast.makeText(LocalContext.current, "Error while loading data", Toast.LENGTH_SHORT).show()
        viewModel!!.errorHasShown()
    } else {
        ProductDetails(state.detailsState, modifier = modifier)
    }

}

@Composable
fun ProductDetails(details: DetailsState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        ImageProduct(modifier = modifier, details!!.image)
        NameProduct(details!!.name)
        Column(
            modifier = modifier.align(Alignment.End)
        ) {
            DiscountText(
                isVisible = details.hasDiscount,
                text = details.discount,
                modifier = modifier.align(Alignment.End)
            )
            PriseProduct(details.price, modifier = modifier.align(Alignment.End))
            AddToCartBtn()
        }

        ProgressBar()
    }
}

@Composable
private fun AddToCartBtn() {
    Button(
        modifier = Modifier.padding(horizontal = 10.dp),
        onClick = {}
    ) {
        Text(
            text = "Add to cart",
            fontSize = 18.sp,
        )
    }
}

@Composable
private fun PriseProduct(prise: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(14.dp),
        text = stringResource(R.string.price_with_arg, prise),
        fontSize = 18.sp,
        color = colorResource(id = ru.otus.common.ui.R.color.purple_500)
    )
}

@Composable
private fun NameProduct(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = name,
        color = Color.Black,
        fontSize = 24.sp

    )
}

@Composable
fun ImageProduct(modifier: Modifier = Modifier, imageUrl: String) {
    Image(
        painter = rememberGlidePainter(
            request = imageUrl,
            previewPlaceholder = R.drawable.ic_launcher_foreground
        ),
        contentDescription = "",
        modifier = modifier
            .fillMaxWidth()
            .size(300.dp),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopStart
    )
}

@Composable
fun DiscountText(
    isVisible: Boolean = false,
    text: String,
    modifier: Modifier = Modifier,

    ) {
    val shape = RoundedCornerShape(
        topStart = 40.dp,
        topEnd = 10.dp,
        bottomEnd = 40.dp,
        bottomStart = 40.dp
    )
    if (isVisible) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,

            modifier = modifier
                .clip(shape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = ru.otus.common.ui.R.color.purple_200),
                            colorResource(id = ru.otus.common.ui.R.color.purple_500)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = shape
                )
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .wrapContentSize()
        )
    }


}

@Composable
fun ProgressBar(
    isVisible: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}