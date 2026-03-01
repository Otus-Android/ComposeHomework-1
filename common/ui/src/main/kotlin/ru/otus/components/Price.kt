package ru.otus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.otus.common.ui.R

@Composable
fun Price(
    value: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .graphicsLayer {
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .background(Color(0xFFFFF3E0.toInt()))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        text = value,
        color = colorResource(R.color.purple_500),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Preview(showBackground = true)
@Composable
private fun Price_Preview() {
    MaterialTheme {
        Price(
            value = "2000 руб"
        )
    }
}