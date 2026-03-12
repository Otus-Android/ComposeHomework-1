package ru.otus.marketsample.products.feature


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.otus.marketsample.R
import ru.otus.marketsample.common.DiscountBadge

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    state: ProductState,
    onItemClicked: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .height(130.dp)
            .clickable { onItemClicked(state.id) },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                model = state.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            if (state.hasDiscount) {
                DiscountBadge(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    discountText = state.discount,
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.name,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.price_with_arg, state.price),
                fontSize = 16.sp,
                color = colorResource(id = ru.otus.common.ui.R.color.purple_500),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = colorResource(id = ru.otus.common.ui.R.color.price_background),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.End)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}