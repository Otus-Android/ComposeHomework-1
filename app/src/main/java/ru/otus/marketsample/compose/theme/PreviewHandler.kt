package ru.otus.marketsample.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import coil3.ColorImage
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePainter
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.compose.asPainter
import coil3.request.ImageRequest

@OptIn(ExperimentalCoilApi::class)
class PreviewHandler : AsyncImagePreviewHandler {
    override suspend fun handle(
        imageLoader: ImageLoader,
        request: ImageRequest
    ): AsyncImagePainter.State {
        return AsyncImagePainter.State.Loading(ColorImage(Color.Blue.toArgb()).asPainter(request.context))
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PreviewHandler.Inject(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides this, content )
}
