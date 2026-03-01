package ru.otus.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.otus.common.ui.R

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    image: @Composable BoxScope.() -> Unit = {},
    badge: @Composable BoxScope.() -> Unit = {},
    title: @Composable ColumnScope.() -> Unit = {},
    price: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier = modifier
            .height(130.dp)
            .background(color = colorResource(R.color.white)),
    ) {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer {
                        shape = RoundedCornerShape(12.dp)
                        clip = true
                    }
            ) {
                image.invoke(this)
                badge.invoke(this)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                title.invoke(this)
                price.invoke(this)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCard_Preview() {
    MaterialTheme {
        ProductCard(
            image = {
                    Image(
                        painter = painterResource(R.drawable.cats),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
            },
            badge = {
                Badge(
                    value = "20%",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )
            },
            title = {
                Text(
                    text = "Персиковый кранч",
                    modifier = Modifier
                        .fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontSize = 18.sp,
                    color = colorResource(R.color.black),
                    fontWeight = FontWeight.Medium
                )
            },
            price = {
                Price(
                    "2000 руб",
                    modifier = Modifier
                        .align(Alignment.End)
                )
            }
        )
    }
}