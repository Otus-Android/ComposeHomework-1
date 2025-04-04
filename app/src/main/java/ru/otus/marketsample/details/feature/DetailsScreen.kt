package ru.otus.marketsample.details.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.glide.rememberGlidePainter
import ru.otus.marketsample.R


@Preview(showSystemUi = true)
@Composable
fun DetailsScreenPreview() {

    DetailsScreen()

}

@Composable
fun DetailsScreen() {
    val uri = ""
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberGlidePainter(
                request = uri,
                previewPlaceholder = R.drawable.ic_launcher_foreground
            ),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}
