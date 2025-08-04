package ru.otus.marketsample.details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.details.feature.DetailsState
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.products.feature.ui.CircularProgressBar

@Composable
fun DetailsScreen(
    id: String,
    modifier: Modifier,
    viewModel: DetailsViewModel
) {
    viewModel.requestProducts(id)
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressBar(Modifier)
            state.hasError -> Toast.makeText(
                LocalContext.current, "Error wile loading data", Toast.LENGTH_SHORT
            ).show()

            else -> Details(state.detailsState)
        }
    }
}

@Composable
fun Details(detailsState: DetailsState) = Column {
    AsyncImage(
        model = detailsState.image,
        contentDescription = "details_image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
    )
    Text(
        text = detailsState.name,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(ru.otus.common.ui.R.color.black)
        ),
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 16.dp)
    )
}