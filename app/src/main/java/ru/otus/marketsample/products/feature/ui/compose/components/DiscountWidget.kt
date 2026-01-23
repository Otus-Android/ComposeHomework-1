package ru.otus.marketsample.products.feature.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun DiscountWidget(
    discount: String,
    modifier: Modifier = Modifier
) {
    val brush = Brush.linearGradient(
        colors = listOf(
            colorResource(ru.otus.common.ui.R.color.purple_200),
            colorResource(ru.otus.common.ui.R.color.purple_500)
        )
    )
    val shape = RoundedCornerShape(
        topStart = 40.dp,
        bottomStart = 40.dp,
        topEnd = 10.dp,
        bottomEnd = 40.dp
    )
    Text(
        text = discount,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = modifier
            .padding(8.dp)
            .background(brush = brush, shape = shape)
            .border(width = 2.dp, color = Color.White, shape = shape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}