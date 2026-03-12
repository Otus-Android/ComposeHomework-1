package ru.otus.marketsample.details.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.common.DiscountBadge

@Composable
fun DetailsContent(
    state: DetailsState,
) {
    Box(
        modifier = Modifier
        .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                model = state.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start),
                text = state.name,
                fontSize = 24.sp,
                color = Color.Black,
            )
            if (state.discount.isNotEmpty()) {
                DiscountBadge(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp),
                    discountText = state.discount,
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(14.dp),
                text = stringResource(R.string.price_with_arg, state.price),
                fontSize = 18.sp,
                color = colorResource(id = ru.otus.common.ui.R.color.purple_500),
            )
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp),
                contentPadding = PaddingValues(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = ru.otus.common.ui.R.color.purple_500)
                ),
                shape = RoundedCornerShape(4.dp),
                onClick = {},
            ) {
                Text(
                    text = "Add to cart".uppercase(), fontSize = 18.sp
                )
            }
        }
    }
}