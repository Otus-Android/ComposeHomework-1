package ru.otus.marketsample.products.feature

import android.widget.Toast;
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable;
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview;
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import ru.otus.marketsample.R

import ru.otus.marketsample.details.feature.DetailsViewModel;
import ru.otus.marketsample.details.feature.DiscountText


@Preview(showSystemUi = true)
@Composable
fun ProductListScreenPreview() {
    ProductListScreen(null)
    /*ProductListScreen(null)*/
    //ItemProduct(ProductState("d", "name", " ", "200", true, "-23%"))
}

@Composable
fun ProductListScreen(viewModel: ProductListViewModel?, modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(ProductsScreenState()) }
    LaunchedEffect(Unit) {
        viewModel!!.state.collect { newState ->
            state = newState
        }
    }
    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
    } else if (state.hasError) {
        Toast.makeText(LocalContext.current, "Error while loading data", Toast.LENGTH_SHORT).show()
        viewModel!!.errorHasShown()
    } else {
        LazyColumn(modifier = modifier.fillMaxSize() ) {
            items(state.productListState){ ItemProduct(it,modifier) }
        }
    }

}

@Composable
fun ItemProduct(productState: ProductState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()

            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .weight(1f)
                .height(130.dp)
        ) {
            ImageProduct(modifier, productState.image)
            DiscountText(
                productState.hasDiscount,
                productState.discount,
                modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .height(130.dp)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            ProductName(name = productState.name, modifier = modifier)
            ProductPrise(prise = productState.price, modifier = modifier.align(Alignment.End))
        }
    }
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
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center
    )
}

@Composable
fun ProductName(name: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = name,
        color = Color.Black,
        fontSize = 18.sp,
        maxLines = 2, fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis,

        )

}

@Composable
fun ProductPrise(prise: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .background(
                color = Color(0xfffff3e0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        text = stringResource(R.string.price_with_arg, prise),
        color = colorResource(id = ru.otus.common.ui.R.color.purple_500),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp

    )
}