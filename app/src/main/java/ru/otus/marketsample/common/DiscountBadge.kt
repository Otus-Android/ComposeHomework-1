package ru.otus.marketsample.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.otus.common.ui.R

enum class BadgeSize {
    L, S
}

@Composable
fun DiscountBadge(
    badgeSize: BadgeSize = BadgeSize.S,
    discount: String
) {
    val badgeShape = remember {
        RoundedCornerShape(
            topStart = 40.dp,
            topEnd = 10.dp,
            bottomStart = 40.dp,
            bottomEnd = 40.dp,
        )
    }

    val fontSize = when(badgeSize) {
        BadgeSize.L -> 20.sp
        BadgeSize.S -> 14.sp
    }

    val fontWeight = when(badgeSize) {
        BadgeSize.L -> FontWeight.Normal
        BadgeSize.S -> FontWeight.Bold
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorResource(R.color.purple_200),
                        colorResource(R.color.purple_500)
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
                shape = badgeShape
            )
            .border(
                width = 2.dp,
                color = Color.White,
                shape = badgeShape
            )

            .padding(
                horizontal = 10.dp,
                vertical = 4.dp,
            )
    ) {
        Text(
            text = discount,
            style = TextStyle.Default.copy(
                color = Color.White,
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        )
    }
}